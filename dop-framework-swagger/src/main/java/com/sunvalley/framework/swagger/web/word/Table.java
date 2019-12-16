package com.sunvalley.framework.swagger.web.word;

import lombok.Data;

import java.util.List;

/**
 * Table
 *
 * @author XiuYin.Cui
 * @date 2018/1/11
 */
@Data
public class Table {
	/**
	 * 一级序号
	 */
	private String seqTitle;
	/**
	 * 大标题
	 */
	private String title;
	/**
	 * 二级序号
	 */
	private String titleVisable;

	private String seqTag;
	/**
	 * 小标题
	 */
	private String tag;
	/**
	 * url
	 */
	private String url;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 作者
	 */
	private String author;
	/**
	 * 接口响应时长
	 */
	private String responseTime;
	/**
	 * 请求参数格式
	 */
	private String requestForm;
	/**
	 * 响应参数格式
	 */
	private String responseForm;
	/**
	 * 请求方式
	 */
	private String requestType;
	/**
	 * 请求体
	 */
	private List<Request> requestList;
	/**
	 * 返回体
	 */
	private List<Response> responseList;
	/**
	 * 请求header参数
	 */
	private String requestHeaderParam;
	/**
	 * 请求参数
	 */
	private String requestParam;
	/**
	 * 返回参数
	 */
	private String responseParam;
}
