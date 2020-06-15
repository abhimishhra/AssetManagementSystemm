package com.cg.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.entity.Employee;
import com.cg.exception.EmployeeAlreadyExistException;
import com.cg.exception.EmployeeNotFoundException;
import com.cg.repository.EmployeeRepository;

@Service
public class EmployeeService {
 
	@Autowired 
	EmployeeRepository repository;
	
	public List<Employee> getAllEmployees(){
		List<Employee> empList = repository.findAll();
		return empList;
	}
	public Employee getEmployeeById(int id) throws EmployeeNotFoundException{
		Optional<Employee> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			throw new EmployeeNotFoundException("Employee Not Present With Id: "+id);
		}
	}
	public List<Employee> getEmployeeByName(String name){
		List<Employee> empList = repository.findByEmpName(name);
		return empList;
	}
	public Employee addEmployee(Employee emp) throws EmployeeAlreadyExistException{
		int id = emp.getEmpNo();
		if(!repository.existsById(id)) {
			return repository.save(emp);
		}
		else {
			throw new EmployeeAlreadyExistException("Employee Already Exists With Id: "+id);
		}
		
	}
	public Employee updateEmployee(Employee emp) throws EmployeeNotFoundException{
		int id = emp.getEmpNo();
		if(repository.existsById(id)) {
			return repository.save(emp);
		}
		else {
			throw new EmployeeNotFoundException("Employee Not Present With Id: "+id);
		}
	}
	public void deleteEmployeeById(int id) throws EmployeeNotFoundException{
		if(repository.existsById(id)) {
			repository.deleteById(id);
		}
		else {
			throw new EmployeeNotFoundException("Employee Not Present With Id: "+id);
		}
	}
}
