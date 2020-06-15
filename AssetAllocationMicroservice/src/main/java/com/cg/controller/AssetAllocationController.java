package com.cg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cg.entity.AssetAllocation;
import com.cg.exception.AllocationAlreadyExistException;
import com.cg.exception.AllocationNotFoundException;
import com.cg.service.AssetAllocationService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
//port: 8082
public class AssetAllocationController {

	@Autowired
	AssetAllocationService service;
	
	Logger logger = LoggerFactory.getLogger(AssetAllocationController.class);
	
	@GetMapping("/allocation/all")
	public List<AssetAllocation> getAll(){
		List<AssetAllocation> allocations =  service.getAllAllocations();
		return allocations;
	}
	@PostMapping("/allocation/add")
	@HystrixCommand(fallbackMethod = "allocationAlreadyExists")
	public AssetAllocation addRequest(@RequestBody AssetAllocation allocation) throws AllocationAlreadyExistException {
		AssetAllocation result = service.addAllocation(allocation);
		logger.info("Allocation Added to the Database");
		return result;
	}
	
	//fallback methods
	
	public AssetAllocation allocationAlreadyExists(@RequestBody AssetAllocation allocation){
		logger.info("Can't Add. Allocation Already Present With Id: "+allocation.getAllocationId());
		return new AssetAllocation(0, 0, 0, null, null);
	}
}
