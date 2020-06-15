package com.cg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.Before;
import com.cg.entity.AssetAllocation;
import com.cg.exception.AllocationAlreadyExistException;
import com.cg.exception.AllocationNotFoundException;
import com.cg.repository.AssetAllocationRepository;
import com.cg.service.AssetAllocationService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AssetAllocationMicroserviceApplicationTests {
	
	@MockBean 
	private AssetAllocationRepository repo;
	@Autowired
	private AssetAllocationService service;
	
	AssetAllocation  e;
	List<AssetAllocation> list;
	
	@Test
	public void contextLoads() {
		
	}
	@Before
	public void setUp() throws Exception{
		e=new AssetAllocation(2010,101,456,LocalDate.of(2020,05,18),LocalDate.of(2020,06,18));
		list=new ArrayList<AssetAllocation>();
		list.add(e);
		
	}
	@Test
	public void testGetAllAllocations_Success(){
		Mockito.when(repo.findAll()).thenReturn(list);
		assertEquals(list,service.getAllAllocations());
	}
	@Test
	public void testGetAllEmployeeForWithZeroValuesPresent_Failure(){
		Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
		assertNotEquals(list, service.getAllAllocations());
	}
	@Test
	public void testAddProgram_Success() throws AllocationAlreadyExistException {
		e= new AssetAllocation(2011,105,458,LocalDate.of(2020,05,18),LocalDate.of(2020,06,18));
		Mockito.when(repo.existsById(e.getAllocationId())).thenReturn(false);
		Mockito.when(repo.save(e)).thenReturn(e);
		assertEquals(e, service.addAllocation(e));
	}

	@Test(expected = AllocationAlreadyExistException.class)
	public void testAddProgramForAlreadyExists_Failure() throws AllocationAlreadyExistException  {
		e = new AssetAllocation(2011,105,458,LocalDate.of(2020,05,18),LocalDate.of(2020,06,18));
		Mockito.when(repo.existsById(e.getAllocationId())).thenReturn(true);
		service.addAllocation(e);
	}
}
