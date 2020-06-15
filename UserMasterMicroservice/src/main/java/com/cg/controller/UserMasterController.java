package com.cg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cg.entity.UserMaster;
import com.cg.exception.InvalidUserException;
import com.cg.service.UserMasterService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
//port: 8084
public class UserMasterController {
	
	@Autowired
	UserMasterService service;
	Logger logger = LoggerFactory.getLogger(UserMasterController.class);
	
	//http://localhost:8084/user/name/pwd
	@GetMapping("/user/{name}/{password}")
	@HystrixCommand(fallbackMethod = "invalidUser")
	public UserMaster getUserByName(@PathVariable("name") String username,
			@PathVariable("password") String password) throws InvalidUserException{
		UserMaster user = service.getUserByName(username, password);
		logger.info("Login Successfull with Username: "+username+"or Password: "+password);
		return user;
	}
	public UserMaster invalidUser(@PathVariable("name") String username,
			@PathVariable("password") String password) {
		logger.info("Invalid Username: "+username+"or Password: "+password);
		return new UserMaster(0, "", "", "", 0);
	}
}
