package com.cg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cg.entity.Asset;
import com.cg.exception.AssetAlreadyExistException;
import com.cg.exception.AssetNotFoundException;
import com.cg.service.AssetService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
//port: 8081
public class AssetController {
	@Autowired
	AssetService service;
	
	Logger logger = LoggerFactory.getLogger(AssetController.class);
	
	@GetMapping(value="asset/all")
	public List<Asset> getAllAssets(){
		List<Asset> assetsList = service.getAllAssets();
		return assetsList;	
	}
	@GetMapping(value="asset/id/{aid}")
	@HystrixCommand(fallbackMethod = "assetNotFoundById")
	public Asset getAssetById(@PathVariable("aid") int id) throws AssetNotFoundException{
		Asset asset = service.getAssetById(id);
		logger.info("Asset Found With Id: "+id);
		return asset;
	}
	@GetMapping(value="asset/name/{aname}")
	@HystrixCommand(fallbackMethod = "assetNotFoundByName")
	public Asset getAssetByName(@PathVariable("aname") String name) throws AssetNotFoundException{
		Asset asset = service.getAssetByName(name);
		logger.info("Asset Found With Name: "+name);
		return asset;
	}
	@PostMapping("asset/add")
	@HystrixCommand(fallbackMethod = "assetAlreadyExists")
	public Asset addAsset(@RequestBody Asset asset) throws AssetAlreadyExistException {
		Asset result = service.addAsset(asset);
		//logger.info("Asset Added to the Database");
		return result;
	}
	@PutMapping(value="asset/update")
	@HystrixCommand(fallbackMethod = "assetNotFoundForUpdate")
	public Asset updateAsset(@RequestBody Asset asset) throws AssetNotFoundException{
		service.updateAsset(asset);
		logger.info("Updated Asset in Database.");
		return asset;
	}
	@DeleteMapping("/asset/delete/{aid}")
	@HystrixCommand(fallbackMethod = "assetNotFoundForDeletion")
	public String deleteAssetById(@PathVariable("aid") int id) throws AssetNotFoundException {
		service.deleteAssetById(id);
		logger.info("Asset Deleted Successfully.");
		return "deleted";
	}
	
	//fallback methods
	
	public Asset assetNotFoundById(@PathVariable("aid") int id) {
		logger.info("Asset Not Found With Id: "+id);
		return new Asset(0,"","",0,"");
	}
	public Asset assetNotFoundByName(@PathVariable("aid") String name) {
		logger.info("Asset Not Found With Name: "+name);
		return new Asset(0,"","",0,"");
	}
	public Asset assetAlreadyExists(@RequestBody Asset asset){
		logger.info("Can't Add. Asset Already Present With Id: "+asset.getAssetId());
		return new Asset(0,"","",0,"");
	}
	public Asset assetNotFoundForUpdate(@RequestBody Asset asset){
		logger.info("Can't Update. Asset Not Present With Id: "+asset.getAssetId());
		return new Asset(0,"","",0,"");
	}
	public String assetNotFoundForDeletion(@PathVariable("aid") int id){
		logger.info("Can't Delete. Asset Not Found With Id: "+id);
		return "Can't Delete. Asset Not Found With Id: "+id;
	}
}
