package com.cg.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.entity.UserMaster;
import com.cg.exception.InvalidUserException;
import com.cg.repository.UserMasterRepository;

@Service 
public class UserMasterService {
	
	@Autowired
	UserMasterRepository repository;
	
	public UserMaster getUserByName(String name, String password) throws InvalidUserException {
		Optional<UserMaster> optional = repository.findByUserName(name);
		if(optional.isPresent()) {
			UserMaster user = optional.get();
			if(user.getPassword().equalsIgnoreCase(password)) {
				return user;
			}
			else {
				throw new InvalidUserException("Invalid Username or password");
			}
		}
		else
			throw new InvalidUserException("Invalid Username or password");
	}
}
