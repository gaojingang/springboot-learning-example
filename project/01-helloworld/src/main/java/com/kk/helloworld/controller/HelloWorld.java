package com.kk.helloworld.controller;

import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Controller;



@Controller
public class HelloWorld {

	

	public Map<String,Object> showHelloWorld(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("msg","HelloWorld!");
		
		
		return map;
	}
	
	
}
