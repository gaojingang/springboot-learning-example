package com.kk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.kk.mapper")  //告诉SpringBoot 整合mybatis 时候扫描mapper接口，生成代理对象
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
	
	
	
	

}
