package com.sunvalley.framework.demo.web.word;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sunvalley.framework.core.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Swagger2Word
 *
 * @author XiuYin.Cui
 * @date 2018/1/12
 */
@Slf4j
public class Swagger2Word {
	private final ObjectMapper mapper = new ObjectMapper();
	private final String author;
	private final String responseTime;

	public Swagger2Word(String author, String responseTime) {
		this.author = author;
		this.responseTime = responseTime;
	}

	/**
	 * 控制一级别标显示还是隐藏
	 */
	private String titleVisable = "1";

	private String convertType(String type) {
		switch (type) {
			case "integer":
				return "Number";
			case "boolean":
				return "Boolean";
			case "string":
				return "String";
			case "array":
				return "Array";
			case "file":
				return "File";
			default:
				return type;
		}
	}

	private List<Request> buildRequest(JsonNode parameters, JsonNode jsonNode) {
		List<Request> requests = new ArrayList<>();
		if (parameters == null || parameters.size() == 0) {
			requests.add(Request.builder().build());
			return requests;
		}
		parameters.forEach(parameter -> {
			String in = parameter.get("in").asText();
			if ("body".equals(in)) {
				JsonNode schemaMap = parameter.get("schema");
				if (schemaMap.hasNonNull("$ref")) {
					String ref = schemaMap.get("$ref").asText().replace("#/definitions/", "");
					JsonNode definitions = jsonNode.get("definitions");
					JsonNode refMap = definitions.get(ref);
					JsonNode properties = refMap.get("properties");
					JsonNode fieldRequiredJsonNode = refMap.get("required");
					Iterator<String> fields = properties.fieldNames();
					while (fields.hasNext()) {
						String field = fields.next();
						JsonNode fieldNode = properties.get(field);
						Request request = Request.builder()
							.parameter(field)
							.type(convertType(fieldNode.at("/type").asText()))
							.parameterName(fieldNode.at("/description").asText())
							.build();
						if (fieldRequiredJsonNode != null) {
							for (JsonNode fieldRequired : fieldRequiredJsonNode) {
								request.setRequire(fieldRequired.textValue().equals(field));
							}
						}
						requests.add(request);
					}
				} else {
					Request request = Request.builder()
						.parameter(parameter.at("/name").asText())
						.require(parameter.at("/required").asBoolean())
						.type(convertType(schemaMap.at("/type").asText()))
						.parameterName(parameter.at("/description").asText())
						.build();
					requests.add(request);
				}
			} else if ("query".equals(in) || "formData".equals(in)) {
				Request request = Request.builder()
					.parameter(parameter.at("/name").asText())
					.require(parameter.at("/required").asBoolean())
					.type(convertType(parameter.at("/type").asText()))
					.parameterName(parameter.at("/description").asText())
					.build();
				requests.add(request);
			} else {
				throw new RuntimeException(in);
			}
		});
		return requests;
	}

	private List<Response> buildResponse(JsonNode parameters, JsonNode jsonNode) {
		List<Response> responses = new ArrayList<>();
		JsonNode resultMap = parameters.get("200");
		if (!resultMap.has("schema")) {
			responses.add(Response.builder().build());
			return responses;
		}
		JsonNode schemaMap = resultMap.get("schema");
		if (!schemaMap.hasNonNull("$ref") && !schemaMap.hasNonNull("items")) {
			Response response = Response.builder()
				.parameter("请补充")
				.type(convertType(schemaMap.get("type").asText()))
				.require(true)
				.parameterName("请添加描述")
				.build();
			responses.add(response);
			return responses;
		}
		if (schemaMap.has("$ref")) {
			String ref = schemaMap.get("$ref").asText().replace("#/definitions/", "");
			JsonNode definitions = jsonNode.get("definitions");
			JsonNode refMap = definitions.get(ref);
			JsonNode properties = refMap.get("properties");

			Iterator<String> fields = properties.fieldNames();
			while (fields.hasNext()) {
				String field = fields.next();
				JsonNode fieldProperties = properties.get(field);
				if (fieldProperties.hasNonNull("items")) {
					((ObjectNode) fieldProperties).put("name", field);
					recursion(fieldProperties, jsonNode, responses);
				} else {
					Response response = Response.builder()
						.parameter(field)
						.type(convertType(fieldProperties.at("/type").asText()))
						.parameterName(fieldProperties.at("/description").asText())
						.require(false)
						.build();
					responses.add(response);
				}
			}
		}

		if (schemaMap.hasNonNull("items")) {
			recursion(schemaMap, jsonNode, responses);
		}
		return responses;
	}

	private void recursion(JsonNode recursionMap, JsonNode jsonNode, List<Response> responses) {
		recursion(recursionMap, jsonNode, responses, null);
	}

	private void recursion(JsonNode recursionMap, JsonNode jsonNode, List<Response> responses, String pRef) {
		Response pResponse = Response.builder()
			.parameter(recursionMap.at("/name").asText("请补充"))
			.type(convertType(recursionMap.get("type").asText()))
			.require(true)
			.parameterName(recursionMap.at("/description").asText("请添加描述"))
			.remark("父节点")
			.build();
		responses.add(pResponse);
		JsonNode itemMap = recursionMap.get("items");
		if (!itemMap.hasNonNull("$ref") && !itemMap.hasNonNull("items")) {
//            Response response = Response.builder()
//                    .parameter("请补充")
//                    .type(convertType((String) itemMap.get("type")))
//                    .require(true)
//                    .parameterName("请添加描述")
//                    .remark("子节点")
//                    .build();
//            responses.add(response);
			return;
		}
		String ref = itemMap.get("$ref").asText().replace("#/definitions/", "");
		// 避免递归死循环
		if (pRef != null && pRef.equals(ref)) {
			return;
		}
		JsonNode definitions = jsonNode.get("definitions");
		JsonNode refMap = definitions.get(ref);
		if (refMap == null) {
			return;
		}
		JsonNode properties = refMap.get("properties");
		if (properties == null) {
			return;
		}
		Iterator<String> fields = properties.fieldNames();
		while (fields.hasNext()) {
			String field = fields.next();
			JsonNode fieldProperties = properties.get(field);
			Response response = Response.builder()
				.parameter(field)
				.type(convertType(fieldProperties.at("/type").asText()))
				.parameterName(fieldProperties.at("/description").asText())
				.require(false)
				.remark("子节点")
				.build();
			responses.add(response);
			if (fieldProperties.hasNonNull("items")) {
				recursion(fieldProperties, jsonNode, responses, ref);
			}
		}
	}

	public List<Table> getTableList(String swaggerJson) {
		try {
			return tableList(swaggerJson);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
	}

	private List<Table> tableList(String swaggerJson) throws IOException {
		Map<String, String> tagsMap = new HashMap<>();
		int seqTitle = 1;
		int seqTag = 1;
		List<Table> result = new ArrayList<>();
		// convert JSON string to Map
		JsonNode jsonNode = mapper.readTree(swaggerJson);
		// 解析tags
		JsonNode tagsPaths = jsonNode.get("tags");
		if (tagsPaths != null) {
			for (JsonNode path : tagsPaths) {
				String name = path.at("/parameter").asText("");
				String description = path.at("/description").asText("");
				tagsMap.put(name, description);
			}
		}
		// 解析paths
		JsonNode paths = jsonNode.get("paths");
		if (paths != null) {
			Iterator<String> fields = paths.fieldNames();
			while (fields.hasNext()) {
				String key = fields.next();
				// 1.请求路径
				// 2.请求方式，类似为 get,post,delete,put 这样
				StringBuilder requestType = new StringBuilder();
				JsonNode value = paths.get(key);
				Iterator<String> requestTypes = value.fieldNames();
				while (requestTypes.hasNext()) {
					requestType.append(requestTypes.next()).append(",");
				}
				Iterator<Map.Entry<String, JsonNode>> it2 = value.fields();
				//不管有几种请求方式，都只解析第一种
				Map.Entry<String, JsonNode> firstRequest = it2.next();
				JsonNode content = firstRequest.getValue();
				// 4. 大标题（类说明）
				String title = content.at("/tags/0").asText();
				// 5.小标题 （方法说明）
				String tag = content.get("summary").asText();
				// 6.接口描述
				String description = content.get("summary").asText();
				// 7.请求参数格式，类似于 multipart/form-data
				StringBuilder requestForm = new StringBuilder();
				JsonNode consumes = content.get("consumes");
				if (consumes != null && consumes.size() > 0) {
					for (JsonNode consume : consumes) {
						requestForm.append(consume.asText()).append(",");
					}
				}
				// 8.返回参数格式，类似于 application/json
				StringBuilder responseForm = new StringBuilder();
				JsonNode produces = content.get("produces");
				if (produces != null && produces.size() > 0) {
					for (JsonNode produce : produces) {
						responseForm.append(produce.asText()).append(",");
					}
				}
				// 9. 请求体
				JsonNode parameters = content.get("parameters");
				List<Request> requestList = buildRequest(parameters, jsonNode);

				// 10.返回体
				JsonNode responses = content.get("responses");
				List<Response> responseList = buildResponse(responses, jsonNode);

				//封装Table
				Table table = new Table();
				if (tagsMap.containsKey(title)) {
					if (!"".equals(tagsMap.get(title))) {
						table.setTitle(tagsMap.get(title));
					} else {
						table.setTitle(title);
					}
				} else {
					table.setTitle(title);
				}
				if (titleVisable.equals(table.getTitle())) {
					table.setTitleVisable("0");
				} else {
					table.setTitleVisable("1");
					table.setSeqTitle("2." + seqTitle + " ");
					seqTitle++;
					seqTag = 1;
				}
				titleVisable = table.getTitle();

				table.setUrl(key);
				table.setTag(tag);
				table.setSeqTag("2." + seqTitle + "." + seqTag + " ");
				seqTag++;
				table.setResponseTime(responseTime);
				table.setAuthor(author);
				table.setDescription(description);
				table.setRequestForm(removeEnd(requestForm.toString(), ","));
				table.setResponseForm(removeEnd(responseForm.toString(), ","));
				table.setRequestType(removeEnd(requestType.toString(), ","));
				table.setRequestList(requestList);
				table.setResponseList(responseList);
				table.setRequestParam(buildParameter(requestList, jsonNode));
				// 取出来状态是200时的返回值
				JsonNode obj = responses.get("200");
				if (obj == null) {
					table.setResponseParam("");
					result.add(table);
					continue;
				}
				JsonNode schema = obj.get("schema");
				if (schema == null) {
					result.add(table);
					continue;
				}
				if (schema.hasNonNull("$ref")) {
					//非数组类型返回值
					String ref = schema.get("$ref").asText();
					//解析swagger2 ref链接
					ObjectNode objectNode = parseRef(ref, jsonNode);
					table.setResponseParam(jsonFormat(objectNode.toString()));
					result.add(table);
					continue;
				} else {
					JsonNode items = schema.get("items");
					if (items != null && items.hasNonNull("$ref")) {
						//数组类型返回值
						String ref = items.get("$ref").asText();
						//解析swagger2 ref链接
						ObjectNode objectNode = parseRef(ref, jsonNode);
						ArrayNode arrayNode = mapper.createArrayNode();
						arrayNode.add(objectNode);
						table.setResponseParam(jsonFormat(arrayNode.toString()));
						result.add(table);
						continue;
					} else {
						//增加对集合返回体的解析 2019-08-16
						JsonNode additionalProperties = schema.get("additionalProperties");
						if (additionalProperties != null) {

                   /*     if (((Map) additionalProperties).get("$ref") != null) {
                            //非数组类型返回值
                            String ref = (String) ((Map) additionalProperties).get("$ref");
                            //解析swagger2 ref链接
                            ObjectNode objectNode = parseRef(ref, map);
                            table.setResponseParam(objectNode.toString());
                            result.add(table);
                            continue;
                        }*/
							JsonNode additionalPropertiesItems = additionalProperties.get("items");
							if (additionalPropertiesItems != null && additionalPropertiesItems.hasNonNull("$ref")) {
								//数组类型返回值
								String ref = additionalPropertiesItems.get("$ref").asText();
								//解析swagger2 ref链接
								ObjectNode objectNode = parseRef(ref, jsonNode);
								ArrayNode arrayNode = mapper.createArrayNode();
								arrayNode.add(objectNode);
								table.setResponseParam(jsonFormat(arrayNode.toString()));
								result.add(table);
								continue;
							} else {
								table.setResponseParam("请补充");
								result.add(table);//返回Map情况进行处理
							}
						} else {
							table.setResponseParam("请补充");
							result.add(table);//返回Map情况进行处理
						}
					}

				}
			}
		}
		return result;
	}

	private ObjectNode parseRef(String ref, JsonNode jsonNode) {
		return parseRef(ref, jsonNode, null);
	}

	/**
	 * 从map中解析出指定的ref
	 *
	 * @param ref      ref链接 例如："#/definitions/PageInfoBT«Customer»"
	 * @param jsonNode 是整个swagger json转成map对象
	 * @return ObjectNode
	 */
	private ObjectNode parseRef(String ref, JsonNode jsonNode, String pRef) {
		ObjectNode objectNode = mapper.createObjectNode();
		if (pRef != null && pRef.equals(ref)) {
			return objectNode;
		}
		if (StringUtils.hasText(ref) && ref.startsWith("#")) {
			String jsonPath = ref.substring(1);
			// json path 语法读取
			JsonNode pathNode = jsonNode.at(jsonPath);
			if (pathNode == null) {
				return objectNode;
			}
			JsonNode properties = pathNode.get("properties");
			if (properties == null) {
				return objectNode;
			}
			Iterator<String> keyIt = properties.fieldNames();
			//遍历key
			while (keyIt.hasNext()) {
				String key = keyIt.next();
				JsonNode keyMap = properties.get(key);
				if (keyMap == null) {
					continue;
				}
				String type = keyMap.at("/type").asText();
				if (StringUtil.isBlank(type)) {
					continue;
				}
				if ("array".equals(type)) {
					//数组的处理方式
					String sonRef = keyMap.at("/items/$ref").asText();
					if (StringUtil.isBlank(sonRef)) {
						continue;
					}
					JsonNode object = parseRef(sonRef, jsonNode, ref);
					ArrayNode arrayNode = mapper.createArrayNode();
					arrayNode.add(object);
					objectNode.set(key, arrayNode);
				} else if (keyMap.hasNonNull("$ref")) {
					//对象的处理方式
					String sonRef = keyMap.get("$ref").asText();
					ObjectNode object = parseRef(sonRef, jsonNode);
					objectNode.set(key, object);
				} else {
					//其他参数的处理方式，string、int
					String str = "";
					if (keyMap.hasNonNull("description")) {
						str = str + keyMap.get("description").asText();
					}
                    /*if (keyMap.get("format") != null) {
                        str = str + String.format("格式为(%s)", keyMap.get("format"));
                    }*/
					if (keyMap.get("type") != null) {
						str = str + String.format("(%s)", keyMap.get("type").asText());
					}
					objectNode.put(key, str);
				}
			}

		}
		return objectNode;
	}


	/**
	 * 封装post请求体
	 *
	 * @param list     list
	 * @param jsonNode jsonNode
	 * @return String
	 */
	private String buildParameter(List<Request> list, JsonNode jsonNode) throws IOException {
		Map<String, Object> paramMap = new HashMap<>(8);
		if (list != null && list.size() > 0) {
			for (Request request : list) {
				String name = request.getParameter();
				String type = request.getType();
				if (!StringUtils.hasText(type)) {
					return "{}";
				}
				switch (type) {
					case "String":
						paramMap.put(name, "");
						break;
					case "Number":
						paramMap.put(name, 0);
						break;
					case "Boolean":
						paramMap.put(name, true);
						break;
					case "body":
						String paramType = request.getParameterName();
						ObjectNode objectNode = parseRef(paramType, jsonNode);
						return objectNode.toString();
					default:
						paramMap.put(name, null);
						break;
				}
			}
		}
		return mapper.writeValueAsString(paramMap);
	}

	private static String jsonFormat(String s) {
		int level = 0;
		//存放格式化的json字符串
		StringBuilder jsonForMatStr = new StringBuilder();
		//将字符串中的字符逐个按行输出
		for (int index = 0; index < s.length(); index++) {
			//获取s中的每个字符
			char c = s.charAt(index);
			//level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
			if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
				jsonForMatStr.append(getLevelStr(level));
			}
			//遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
			switch (c) {
				case '{':
				case '[':
					jsonForMatStr.append(c).append('\n');
					level++;
					break;
				case ',':
					jsonForMatStr.append(c).append('\n');
					break;
				case '}':
				case ']':
					jsonForMatStr.append('\n');
					level--;
					jsonForMatStr.append(getLevelStr(level));
					jsonForMatStr.append(c);
					break;
				default:
					jsonForMatStr.append(c);
					break;
			}
		}
		return jsonForMatStr.toString();
	}

	private static String getLevelStr(int level) {
		StringBuilder levelStr = new StringBuilder();
		for (int i = 0; i < level; i++) {
			levelStr.append('\t');
		}
		return levelStr.toString();
	}

	private static String removeEnd(String str, String remove) {
		if (StringUtils.hasText(str) && StringUtils.hasText(remove)) {
			return str.endsWith(remove) ? str.substring(0, str.length() - remove.length()) : str;
		} else {
			return str;
		}
	}
}
