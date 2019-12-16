package com.sunvalley.framework.swagger.web;

import com.sunvalley.framework.swagger.web.word.Swagger2WordService;
import org.beetl.core.GroupTemplate;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 由于 mybatis 的代码生成也是用的 beetl 所以 web to word 里我们也用的 beetl
 *
 * @author dream.lu
 */
@Configuration
@ConditionalOnClass(GroupTemplate.class)
public class BeetlConfiguration {

	private static GroupTemplate groupTemplate() {
		try {
			ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
			org.beetl.core.Configuration cfg = org.beetl.core.Configuration.defaultConfiguration();
			return new GroupTemplate(resourceLoader, cfg);
		} catch (IOException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Bean
	public Swagger2WordService swagger2WordService() {
		GroupTemplate groupTemplate = groupTemplate();
		return new Swagger2WordService(groupTemplate);
	}
}
