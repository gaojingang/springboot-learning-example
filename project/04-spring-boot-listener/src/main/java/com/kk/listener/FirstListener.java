package com.kk.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *  
 * @author gaoji
 * 
 *  <listener>
 *  	<listener-class>com.kk.listener.FirstListener</listener>
 *	<listener>
 *
 */


@WebListener
public class FirstListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("FirstListener.contextInitialized() ");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("FirstListener.contextDestroyed() ");
	}

	
	
	
}
