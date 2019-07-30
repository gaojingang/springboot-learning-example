package com.kk.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kk.pojo.User;
import com.kk.services.UserServices;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServices userServices;
	
	/**
	 * 页面跳转
	 * 
	 * 
	 * */
	
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
	
	
	/**
	 * 添加用户
	 * */
	@RequestMapping("/addUser")
	public String addUser(User user) {
		this.userServices.addUser(user);
		return "ok";
	}
	
	
	/**
	 * 查看所有用户
	 * */
	@RequestMapping("/findUserAll")
	public String findUserAll(Model model) {
		List<User> list = this.userServices.selectUserAll();
		model.addAttribute("list",list);
		return "showUsers";
	}
	
	
}
