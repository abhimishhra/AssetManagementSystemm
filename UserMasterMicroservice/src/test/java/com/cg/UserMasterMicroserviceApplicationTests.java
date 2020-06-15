package com.cg;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cg.entity.UserMaster;
import com.cg.exception.InvalidUserException;
import com.cg.repository.UserMasterRepository;
import com.cg.service.UserMasterService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMasterMicroserviceApplicationTests {
	
	@MockBean
	UserMasterRepository repo;
	
	@Autowired
	UserMasterService service;
	UserMaster user;
	@Test
	void contextLoads() {
	}
	@Test
	public void getUserSuccessfully() throws InvalidUserException {
		user= new UserMaster(1001,"Abhishek","123456","admin",456);
		assertEquals(user,service.getUserByName("Abhishek", "123456"));
	}

}
