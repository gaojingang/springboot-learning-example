package com.kk.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kk.mapper.UserMapper;
import com.kk.pojo.User;
import com.kk.services.UserServices;

@Service
@Transactional
public class UserServiceImpl implements UserServices {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void addUser(User user) {
		this.userMapper.insertUser(user);
		
	}

	@Override
	public List<User> selectUserAll() {
		return userMapper.selectUserAll();
	}

}
