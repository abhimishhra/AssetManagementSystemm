package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.entity.AssetForm;
import com.cg.exception.RequestAlreadyExistException;
import com.cg.exception.RequestNotFoundException;
import com.cg.repository.AssetFormRepository;

@Service
public class AssetFormService {
	
	@Autowired
	AssetFormRepository repository;
	
	public List<AssetForm> getAllForms(){
		List<AssetForm> forms = repository.findAll();
		return forms;
	}
	public String checkstatus(int id) throws RequestNotFoundException {
		Optional<AssetForm> optional = repository.findById(id);
		if(optional.isPresent()) {
			AssetForm form = optional.get();
			return form.getStatus();
		}
		else {
			throw new RequestNotFoundException("Request Not Present With id: "+id);
		}
	}
	public AssetForm addForm(AssetForm form) throws RequestAlreadyExistException {
		int id = form.getRequestId();
		if(!repository.existsById(id)) {
			form.setStatus("Pending");
			return repository.save(form);
		}
		else {
			throw new RequestAlreadyExistException("Request Already Exists With Id: "+id);
		}
	}
	public void allocate(AssetForm form) throws RequestNotFoundException{
		int id = form.getRequestId();
		if(repository.existsById(id)) {
			form.setStatus("Allocated");
			repository.save(form);
		}
		else {
			throw new RequestNotFoundException("Request Not Present With id: "+id);
		}
		
	}
	public void reject(AssetForm form) throws RequestNotFoundException {
		int id = form.getRequestId();
		if(repository.existsById(id)) {
			form.setStatus("Rejected");
			repository.save(form);
		}
		else {
			throw new RequestNotFoundException("Request Not Present With id: "+id);
		}
	}
}
