package com.kk.mapper;

import java.util.List;

import com.kk.pojo.User;

public interface UserMapper {

	
	void insertUser(User user);
	
	List<User> selectUserAll();
	
	
}
