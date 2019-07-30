package com.kk;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class App {
		
	@RequestMapping("/")
	public String index() {
		return "Hello Spring Boot!";
	}
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
		

	}
	
	/*
	 * 设置文件上传限制 
	 * spring.servlet.multipart.max-file-size=80MB
	 * spring.servlet.multipart.max-request-size=80MB
	 */
	
//	@Bean
//	public MultipartConfigElement getMultiPartConfig () {
//		MultipartConfigFactory factory = new MultipartConfigFactory();
//		factory.setMaxFileSize(DataSize.ofMegabytes(80*1024*1024));
//		factory.setMaxRequestSize(DataSize.ofMegabytes(80*1024*1024));
//		
//		return factory.createMultipartConfig();
//		
//	}
//	
}
