package com.cg.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cg.entity.AssetForm;
import com.cg.exception.RequestAlreadyExistException;
import com.cg.exception.RequestNotFoundException;
import com.cg.service.AssetFormService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
//port: 8083
public class AssetFormController {

	@Autowired
	AssetFormService service;
	
	Logger logger = LoggerFactory.getLogger(AssetFormController.class);
	
	@GetMapping("/form/all")
	public List<AssetForm> getAll(){
		List<AssetForm> forms =  service.getAllForms();
		return forms;
	}
	@PostMapping(value="/form/add", consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@HystrixCommand(fallbackMethod = "requestAlreadyExists")
	public AssetForm addForm(@RequestBody AssetForm form) throws RequestAlreadyExistException{
		System.out.println("in ms:"+form);
		AssetForm result = service.addForm(form);
		logger.info("Request Added to the Database");
		return result;
	}
	@GetMapping("form/status/{fid}")
	@HystrixCommand(fallbackMethod = "requestNotFoundById")
	public String checkStatus(@PathVariable("fid") int id) throws RequestNotFoundException {
		String result =  service.checkstatus(id);
		logger.info("Status Checking Successful");
		return result;
	}
	@PutMapping("form/allocate")
	@HystrixCommand(fallbackMethod = "requestNotFoundForAllocation")
	public String allocate(@RequestBody AssetForm form) throws RequestNotFoundException{
		service.allocate(form);
		logger.info("Asset Allocated");
		return "Allocated";
	}
	@PutMapping("form/reject")
	@HystrixCommand(fallbackMethod = "requestNotFoundForRejection")
	public String reject(@RequestBody AssetForm form) throws RequestNotFoundException  {
		service.reject(form);
		logger.info("Request Rejected");
		return "Rejected";
	}
	
	//fallback methods
	
		public AssetForm requestAlreadyExists(@RequestBody AssetForm form){
			logger.info("Can't Add. Request Already Present With Id: "+form.getRequestId());
			return new AssetForm(0, 0, 0, "", null, "");
		}
		public String requestNotFoundById(@PathVariable("aid") int id) {
			logger.info("Request Not Found With Id: "+id);
			return "Request Not Found With Id: "+id;
		}
		public String requestNotFoundForAllocation(@RequestBody AssetForm form) {
			logger.info("Asset Allocation Unsuccessful");
			return "Asset Allocation Unsuccessful";
		}
		public String requestNotFoundForRejection(@RequestBody AssetForm form) {
			logger.info("Request Rejection Unsuccessful");
			return "Request Rejection Unsuccessful";
		}
}
