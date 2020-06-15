package com.cg.entity;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Employee {
 
	private int empNo;
	@NotNull
	@Size(min=3, message = "Employee name should be of atleast 3 characters")
	private String empName;
	@NotNull
	@Size(min=3, message = "Job should be of atleast 3 characters")
	private String job;
	@Positive
	private int managerId;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate hireDate;
	@Positive
	private int deptId;
	@Positive
	private double salary;
	
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Employee(int empNo, String empName, String job, int managerId, LocalDate hireDate, int deptId, double salary) {
		super();
		this.empNo = empNo;
		this.empName = empName;
		this.job = job;
		this.managerId = managerId;
		this.hireDate = hireDate;
		this.deptId = deptId;
		this.salary = salary;
	}
	
	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	@Override
	public String toString() {
		return "Employee [empNo=" + empNo + ", empName=" + empName + ", job=" + job + ", managerId=" + managerId
				+ ", hireDate=" + hireDate + ", deptId=" + deptId + ", salary=" + salary + "]";
	}
	
}
