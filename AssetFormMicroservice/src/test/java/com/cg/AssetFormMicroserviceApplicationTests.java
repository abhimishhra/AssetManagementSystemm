package com.cg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.cg.entity.AssetForm;
import com.cg.exception.RequestAlreadyExistException;
import com.cg.exception.RequestNotFoundException;
import com.cg.repository.AssetFormRepository;
import com.cg.service.AssetFormService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AssetFormMicroserviceApplicationTests {

	@MockBean
	AssetFormRepository repo;
	@Autowired
	AssetFormService service;
	AssetForm form;
	List<AssetForm> list;
	@Test
	public void contextLoads() {
	}
	
	@Before
	public void setUp() throws Exception{
		form=new AssetForm(201,101,456,"Mouse",LocalDate.of(2020, 03, 21),"Pending");
		list=new ArrayList<AssetForm>();
		list.add(form);
	}
	@Test
	public void testGetAllAllocations_Success(){
		Mockito.when(repo.findAll()).thenReturn(list);
		assertEquals(list,service.getAllForms());
	}
	@Test
	public void testGetAllAllocationsForWithZeroValuesPresent_Failure(){
		Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
		assertNotEquals(list, service.getAllForms());
	}
	@Test
	public void testAddAsset_Success() throws RequestAlreadyExistException{
		form= new  AssetForm(201,101,456,"Mouse",LocalDate.of(2020, 03, 21),"Pending");
		Mockito.when(repo.existsById(form.getRequestId())).thenReturn(false);
		Mockito.when(repo.save(form)).thenReturn(form);
		assertEquals(form, service.addForm(form));
	}

	@Test(expected = RequestAlreadyExistException.class)
	public void testAddAssetForAlreadyExists_Failure() throws RequestAlreadyExistException {
		form = new  AssetForm(201,101,456,"Mouse",LocalDate.of(2020, 03, 21),"Pending");
		Mockito.when(repo.existsById(form.getRequestId())).thenReturn(true);
		service.addForm(form);
	}
	@Test 
	public void testAllocateAssetSuccessfully() throws RequestNotFoundException{
		form= new  AssetForm(201,101,456,"Mouse",LocalDate.of(2020, 03, 21),"Pending");
		Mockito.when(repo.existsById(form.getRequestId())).thenReturn(true);
		service.allocate(form);
	}
	@Test(expected = RequestNotFoundException.class)
	public void testAllocateAssetUnsuccessful() throws RequestNotFoundException{
		form= new  AssetForm(201,101,456,"Mouse",LocalDate.of(2020, 03, 21),"Pending");
		Mockito.when(repo.existsById(form.getRequestId())).thenReturn(false);
		service.allocate(form);
	}
	@Test 
	public void testRejectAssetSuccessfully() throws RequestNotFoundException{
		form= new  AssetForm(201,101,456,"Mouse",LocalDate.of(2020, 03, 21),"Pending");
		Mockito.when(repo.existsById(form.getRequestId())).thenReturn(true);
		service.reject(form);
	}
	@Test(expected = RequestNotFoundException.class)
	public void testRejectAssetUnsuccessful() throws RequestNotFoundException{
		form= new  AssetForm(201,101,456,"Mouse",LocalDate.of(2020, 03, 21),"Pending");
		Mockito.when(repo.existsById(form.getRequestId())).thenReturn(false);
		service.reject(form);
	}
}
