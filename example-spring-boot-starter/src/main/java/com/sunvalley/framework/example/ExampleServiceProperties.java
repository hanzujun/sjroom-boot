package com.sunvalley.framework.example;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("example.service")
public class ExampleServiceProperties {
	private String prefix;
	private String suffix;
	private String test2;
}
