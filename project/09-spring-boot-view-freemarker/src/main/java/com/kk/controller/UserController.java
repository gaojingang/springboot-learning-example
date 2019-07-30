package com.kk.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kk.pojo.User;

/**
 * Spring 整合JSP
 * @author gaoji
 *
 */

@Controller
public class UserController {

	@RequestMapping("/showUser")
	public String showUser(Model model) {
		List<User> list = new ArrayList<User>();
		list.add(new User(1, "张三", 22));
		list.add(new User(2, "李四", 23));
		list.add(new User(3, "王五", 24));
		
		model.addAttribute("users",list);
		
		//跳转
		return "userList";
	}
	
	
}
