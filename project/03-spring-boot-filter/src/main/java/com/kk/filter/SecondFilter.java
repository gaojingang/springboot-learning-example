package com.kk.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SecondFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("SecondFilter doFilter() enter");
		
		System.out.println("SecondFilter doFilter() =====================");
		
		System.out.println("SecondFilter doFilter() Leave");
	}



}
