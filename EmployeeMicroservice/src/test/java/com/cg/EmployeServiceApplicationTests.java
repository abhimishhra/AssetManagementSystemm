package com.cg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cg.entity.Employee;
import com.cg.exception.EmployeeAlreadyExistException;
import com.cg.exception.EmployeeNotFoundException;
import com.cg.repository.EmployeeRepository;
import com.cg.service.EmployeeService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EmployeServiceApplicationTests {

	@MockBean 
	private EmployeeRepository repo;
	@Autowired
	private EmployeeService service;
	
	Employee  e;
	List<Employee> list;
	
	@Test
	public void contextLoads() {
		
	}
	
	@Before
	public void setUp() throws Exception{
		e=new Employee(456,"Abhishek","Software Engineer",501,LocalDate.of(2020,05,18),201,50000.23);
		list=new ArrayList<Employee>();
		list.add(e);
		
	}
	@Test
	public void testGetAllEmployees_Success(){
		Mockito.when(repo.findAll()).thenReturn(list);
		assertEquals(list,service.getAllEmployees());
	}

	public void testGetAllEmployeeForWithZeroValuesPresent_Failure(){
		Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
		assertNotEquals(list, service.getAllEmployees());
	}

	@Test
	public void testGetEmployeeByIdFound_Success() throws EmployeeNotFoundException {
		int id = 1;
		Optional<Employee> opt = Optional.of(e);
		Mockito.when(repo.findById(id)).thenReturn(opt);
		assertEquals(e, service.getEmployeeById(id));
	}

	@Test(expected =EmployeeNotFoundException.class)
	public void testGetProgramByIdNotFound_Failure() throws EmployeeNotFoundException {
		int id = 1;
		service.getEmployeeById(id);
	
}
	@Test
	public void testAddEmployee_Success() throws EmployeeAlreadyExistException {
		e= new Employee(460,"Samiran","Software Engineer",501,LocalDate.of(2020,05,18),201,50000.23);
		Mockito.when(repo.existsById(e.getEmpNo())).thenReturn(false);
		Mockito.when(repo.save(e)).thenReturn(e);
		assertEquals(e, service.addEmployee(e));
	}

	@Test(expected = EmployeeAlreadyExistException.class)
	public void testAddProgramForAlreadyExists_Failure() throws EmployeeAlreadyExistException {
		e = new Employee(460,"Samiran","Software Engineer",501,LocalDate.of(2020,05,18),201,50000.23);
		Mockito.when(repo.existsById(e.getEmpNo())).thenReturn(true);
		service.addEmployee(e);
	}

	@Test
	public void testUpdateEmployeeById_Success() throws EmployeeNotFoundException  {
		e= new Employee(456,"Abhishek123","Software Engineer",501,LocalDate.of(2020,05,18),201,50000.23);
		Mockito.when(repo.existsById(e.getEmpNo())).thenReturn(true);
		Mockito.when(repo.save(e)).thenReturn(e);
		assertEquals(e, service.updateEmployee(e));
	}
	
	@Test(expected = EmployeeNotFoundException.class)
	public void testUpdateProgramByIdNotFound_Failure() throws EmployeeNotFoundException {
		e = new Employee(456,"Abhishek","Software Engineer",501,LocalDate.of(2020,05,18),201,50000.23);
		Mockito.when(repo.existsById(e.getEmpNo())).thenReturn(false);
		service.updateEmployee(e);
	}

}
