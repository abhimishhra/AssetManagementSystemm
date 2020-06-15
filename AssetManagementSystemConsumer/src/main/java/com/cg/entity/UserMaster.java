package com.cg.entity;

public class UserMaster {

	private int userId;
	private String userName;
	private String password;
	private String userType;
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