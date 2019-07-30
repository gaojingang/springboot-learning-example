package com.kk.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kk.pojo.User;

@Service
@Transactional
public interface UserServices {

	void addUser(User user);
	
	List<User> selectUserAll();
	
}
