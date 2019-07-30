package com.kk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.kk.filter.SecondFilter;
import com.kk.servlet.FirstServlet;

@SpringBootApplication
public class App2 {
	
	public static void main(String[] args) {
		SpringApplication.run(App2.class, args);
	}
	
	
	@Bean
	public ServletRegistrationBean getServletRegBean() {
			
		ServletRegistrationBean bean = new ServletRegistrationBean(new FirstServlet());
		bean.addUrlMappings("/second");
		
		return bean;
		
	}
	
	@Bean
	public FilterRegistrationBean getFilterRegBean() {
		FilterRegistrationBean bean = new FilterRegistrationBean(new SecondFilter());
		bean.addUrlPatterns(new String[] {"*.do","*.jsp"});
		bean.addUrlPatterns("/second");
		
		return bean;
		
		
	}
	

}
