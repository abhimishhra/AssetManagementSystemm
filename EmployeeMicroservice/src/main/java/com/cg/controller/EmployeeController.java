package com.cg.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cg.entity.Employee;
import com.cg.exception.EmployeeAlreadyExistException;
import com.cg.exception.EmployeeNotFoundException;
import com.cg.service.EmployeeService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
//port: 8085
public class EmployeeController {
	
	@Autowired
	EmployeeService service;
	
	Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	
	@GetMapping("/emp/all")
	public List<Employee> getAllEmployees(){
		List<Employee> empList = service.getAllEmployees();
		return empList;
	}
	@GetMapping("/emp/id/{id}")
	@HystrixCommand(fallbackMethod = "employeeNotFoundById")
	public Employee getEmployeeById(@PathVariable("id") int eid) throws EmployeeNotFoundException {
		Employee emp = service.getEmployeeById(eid);
		logger.info("Employee Found With Id: "+eid);
		return emp;
	}
	@GetMapping("/emp/name/{name}")
	public List<Employee> getEmployeeByName(@PathVariable("name") String ename){
		List<Employee> empList = service.getEmployeeByName(ename);
		return empList;
	}
	@PostMapping("/emp/add")
	@HystrixCommand(fallbackMethod = "employeeAlreadyExists")
	public Employee addEmployee(@RequestBody Employee emp) throws EmployeeAlreadyExistException {
		Employee result = service.addEmployee(emp);
		logger.info("Employee Added to the Database");
		return result;
	}
	@PutMapping("/emp/update")
	@HystrixCommand(fallbackMethod = "employeeNotFoundForUpdate")
	public Employee updateEmployee(@RequestBody Employee emp) throws EmployeeNotFoundException{
		service.updateEmployee(emp);
		logger.info("Updated Employee in Database.");
		return emp;
	}
	@DeleteMapping("/emp/delete/{id}")
	@HystrixCommand(fallbackMethod = "employeeNotFoundForDeletion")
	public String deleteEmployeeById(@PathVariable("id") int eid) throws EmployeeNotFoundException {
		service.deleteEmployeeById(eid);
		logger.info("Employee Deleted Successfully.");
		return "deleted";
	}
	
	//fallback methods
	
	public Employee employeeNotFoundById(@PathVariable("eid") int id) {
		logger.info("Employee Not Found With Id: "+id);
		return new Employee(0, "", "", 0, null, 0, 0.0);
	}
	public List<Employee> employeeNotFoundByName(@PathVariable("aid") String name) {
		logger.info("Employee Not Found With Name: "+name);
		return null;
	}
	public Employee employeeAlreadyExists(@RequestBody Employee emp){
		logger.info("Can't Add. Employee Already Present With Id: "+emp.getEmpNo());
		return new Employee(0, "", "", 0, null, 0, 0.0);
	}
	public Employee employeeNotFoundForUpdate(@RequestBody Employee emp){
		logger.info("Can't Update. Employee Not Present With Id: "+emp.getEmpNo());
		return new Employee(0, "", "", 0, null, 0, 0.0);
	}
	public String employeeNotFoundForDeletion(@PathVariable("aid") int id){
		logger.info("Can't Delete. Employee Not Present With Id: "+id);
		return "Can't Delete. Employee Not Present With Id: "+id;
	}
	
}
