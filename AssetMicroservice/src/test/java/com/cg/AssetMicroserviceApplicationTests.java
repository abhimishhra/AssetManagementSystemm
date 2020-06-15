package com.cg;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

import com.cg.entity.Asset;
import com.cg.exception.AssetAlreadyExistException;
import com.cg.exception.AssetNotFoundException;
import com.cg.repository.AssetRepository;
import com.cg.service.AssetService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class AssetMicroserviceApplicationTests {

	@MockBean 
	private AssetRepository repo;
	@Autowired
	private AssetService service;
	
	Asset e;
	List<Asset> list;
	
	@Test
	public void contextLoads() {
		
	}
	
	@Before
	public void setUp() throws Exception{
		e=new Asset(110,"Mouse","iball",10,"Available");
		list=new ArrayList<Asset>();
		list.add(e);
		
	}
	@Test
	public void testGetAllAssets_Success(){
		Mockito.when(repo.findAll()).thenReturn(list);
		assertEquals(list,service.getAllAssets());
	}
	@Test
	public void testGetAllAssetsForWithZeroValuesPresent_Failure(){
		Mockito.when(repo.findAll()).thenReturn(new ArrayList<>());
		assertNotEquals(list, service.getAllAssets());
	}

	@Test
	public void testGetAssetByIdFound_Success() throws AssetNotFoundException{
		int id = 110;
		Optional<Asset> opt = Optional.of(e);
		Mockito.when(repo.findById(id)).thenReturn(opt);
		assertEquals(e, service.getAssetById(id));
	}

	@Test(expected =AssetNotFoundException.class)
	public void testGetProgramByIdNotFound_Failure() throws AssetNotFoundException {
		int id = 1;
		service.getAssetById(id);
	
}
	@Test
	public void testAddAsset_Success() throws AssetAlreadyExistException {
		e= new Asset(120,"Keyboard","iball",10,"Available");
		Mockito.when(repo.existsById(e.getAssetId())).thenReturn(false);
		Mockito.when(repo.save(e)).thenReturn(e);
		assertEquals(e, service.addAsset(e));
	}

	@Test(expected = AssetAlreadyExistException.class)
	public void testAddAssetForAlreadyExists_Failure() throws AssetAlreadyExistException {
		e = new Asset(110,"Keyboard","iball",10,"Available");
		Mockito.when(repo.existsById(e.getAssetId())).thenReturn(true);
		service.addAsset(e);
	}

	
	@Test(expected = AssetNotFoundException.class)
	public void testUpdateAssetByIdNotFound_Failure() throws AssetNotFoundException {
		e = new Asset(110,"Keyboard","iball",11,"Available");
		Mockito.when(repo.existsById(e.getAssetId())).thenReturn(false);
		service.updateAsset(e);
	}

	@Test
	public void testUpdateAssetById_Success() throws AssetNotFoundException  {
		e= new Asset(110,"Keyboard","iball",13,"Available");
		Mockito.when(repo.existsById(e.getAssetId())).thenReturn(true);
		Mockito.when(repo.save(e)).thenReturn(e);
		assertEquals(e, service.updateAsset(e));
	}
	
	@Test
	public void testDeleteAsset_Success() throws AssetNotFoundException{
		int id=110;
		e=new Asset(110,"Mouse","iball",10,"Available");
		Mockito.when(repo.existsById(id)).thenReturn(true);
		assertEquals(true, service.deleteAssetById(id));
	}
	
	@Test(expected=AssetNotFoundException.class)
	public void testDeleteAssetForNotFound_Failure() throws AssetNotFoundException{
		int id=1;
		Mockito.when(repo.existsById(id)).thenReturn(false);
		service.deleteAssetById(id);
	}
	
}


