package com.kk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/***
 * 
 * @author gaoji
 * 
 * SpringBoot 整合Filter  方式一
 * <filter>
 * 	<filter-name>FirstFilter</filter-name>
 * 	<filter-class>com.kk.filter.FirstFilter</filter-class>
 * </filter>
 * <filter-mapping>
 * 	<filter-name>FirstFilter</filter-name>
 * 	<url-pattern>/first</url-pattern>
 * </filter-mapping>
 * 
 *
 */


//@WebFilter(filterName="FirstFilter",urlPatterns={"*.do","*.jsp"})
@WebFilter(filterName="FirstFilter",urlPatterns={"/first"})
public class FirstFilter implements Filter {


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("enter do filter");
		
		
		System.out.println("=-==============");
		
		System.out.println("leave filter");

	}

}
