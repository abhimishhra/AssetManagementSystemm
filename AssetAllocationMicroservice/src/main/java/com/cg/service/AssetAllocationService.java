package com.cg.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.entity.AssetAllocation;
import com.cg.exception.AllocationAlreadyExistException;
import com.cg.exception.AllocationNotFoundException;
import com.cg.repository.AssetAllocationRepository;

@Service
public class AssetAllocationService {
	@Autowired
	AssetAllocationRepository repository;
	
	public List<AssetAllocation> getAllAllocations(){
		List<AssetAllocation> allocations = repository.findAll();
			return allocations;
	}
	public AssetAllocation addAllocation(AssetAllocation allocation) throws AllocationAlreadyExistException {
		int id = allocation.getAllocationId();
		if(repository.existsById(id)) {
			throw new AllocationAlreadyExistException("Allocation Already Exists With Id: "+id);
		}
		else {
			return repository.save(allocation);
			
		}
	}
}
