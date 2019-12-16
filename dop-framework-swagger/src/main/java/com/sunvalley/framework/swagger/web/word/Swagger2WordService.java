package com.sunvalley.framework.swagger.web.word;

import com.sunvalley.framework.core.io.FastStringWriter;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;

import java.util.List;

/**
 * Swagger2Word 服务
 *
 * @author dream.lu
 */
public class Swagger2WordService {
	private final GroupTemplate groupTemplate;

	public Swagger2WordService(GroupTemplate groupTemplate) {
		this.groupTemplate = groupTemplate;
	}

	public String toIndex(List<String> serviceList) {
		Template template = groupTemplate.getTemplate("/META-INF/templates/swaggerList.html");
		FastStringWriter fastStringWriter = new FastStringWriter();
		template.binding("services", serviceList);
		template.renderTo(fastStringWriter);
		return fastStringWriter.toString();
	}

	public String toWord(String author, String responseTime, String jsonString) {
		Swagger2Word swagger2Word = new Swagger2Word(author, responseTime);
		List<Table> tableList = swagger2Word.getTableList(jsonString);
		Template template = groupTemplate.getTemplate("/META-INF/templates/swagger2word.html");
		FastStringWriter fastStringWriter = new FastStringWriter();
		template.binding("tables", tableList);
		template.renderTo(fastStringWriter);
		return fastStringWriter.toString();
	}
}
