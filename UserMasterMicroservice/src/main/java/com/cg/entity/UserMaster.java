package com.cg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name="user_master")
public class UserMaster {
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	@NotNull
	@Size(min = 4, message = "Username should be of atleast 4 characters")
	@Column(name="user_name")
	private String userName;
	@NotNull
	@Size(min = 6, message = "Password should be of atleast 6 characters")
	@Column(name="password")
	private String password;
	@NotNull
	@Size(min = 3, message = "User type should be of atleast 3 characters")
	@Column(name="user_type")
	private String userType;
	@Positive
	@Column(name="emp_id")
	private int empId;
	
	public UserMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserMaster(int userId, String userName, String password, String userType, int empId) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.userType = userType;
		this.empId = empId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	@Override
	public String toString() {
		return "UserMaster [userId=" + userId + ", userName=" + userName + ", password=" + password + ", userType="
				+ userType + ", empId=" + empId + "]";
	}
	
}