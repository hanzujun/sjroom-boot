package com.sunvalley.framework.demo.web;


import com.sunvalley.framework.core.result.ResultAssert;
import com.sunvalley.framework.demo.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <B>说明：数据字典表 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-05-27 08:34
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/base/dict")
@Api("数据字典表 控制器")
public class DictController {

	@Autowired
	private DemoService demoService;

	@ApiOperation("refresh")
	@PostMapping("refresh")
	public void refresh() {
		demoService.echo();
	}
}
