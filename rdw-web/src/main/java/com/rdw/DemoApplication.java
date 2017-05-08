package com.rdw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

import java.net.MalformedURLException;

@SpringBootApplication
@EnableConfigurationProperties
public class DemoApplication  {

	public static void main(String[] args) throws MalformedURLException {
		ApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
	}
}
