package com.sunvalley.framework.swagger.web.word;

import lombok.Builder;
import lombok.Data;

/**
 * Response
 *
 * @author XiuYin.Cui
 * @date 2018/1/11
 */
@Builder
@Data
public class Response {

	/**
	 * 参数
	 */
	private String parameter;
	/**
	 * 数据类型
	 */
	private String type;
	/**
	 * 参数名称
	 */
	private String parameterName;
	/**
	 * 是否必填
	 */
	private Boolean require;
	/**
	 * 说明
	 */
	private String remark;

}
