package com.cg.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import javax.swing.text.html.parser.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.cg.entity.Asset;
import com.cg.entity.AssetAllocation;
import com.cg.entity.AssetForm;
import com.cg.entity.Employee;
import com.cg.entity.UserMaster;

@RestController
@CrossOrigin
//port: 8086
public class ConsumerController {
	@Autowired
	RestTemplate tmp;
	public RestTemplate getRestTemplet() {
		return tmp;
	}
	public void setRestTemplet(RestTemplate restTemplet) {
		this.tmp = restTemplet;
	}
	
	@GetMapping("/login/{uname}/{pwd}")
	public ResponseEntity logIn(@PathVariable("uname") String username, 
			@PathVariable("pwd") String password) {
		String url = "http://usermaster-service/user/"+username+"/"+password;
		UserMaster user = tmp.getForObject(url, UserMaster.class);
		if(user.getUserId()==0) {
			return new ResponseEntity("Invalid Username or Password", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(user, HttpStatus.OK);
	}
	@GetMapping("/getEmployee/all")
	public ResponseEntity getAllEmployee(){
		String url = "http://employee-service/emp/all";
		List<Employee> employees = tmp.getForObject(url, List.class);
		if(employees.size()==0) {
			return new ResponseEntity("Employees Not Available", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(employees, HttpStatus.OK);
	}
	@GetMapping("/getEmployeeBy/id/{eid}")
	public ResponseEntity getEmployeeById(@PathVariable("eid") int id) {
		String url = "http://employee-service/emp/id/"+id;
		Employee employee =  tmp.getForObject(url, Employee.class);
		if(employee.getEmpNo()==0) {
			return new ResponseEntity("Employee Not Available With Id: "+id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(employee, HttpStatus.OK);
	}
	@GetMapping("/getEmployeeBy/name/{ename}")
	public ResponseEntity getEmployeeByName(@PathVariable("ename") String name) {
		String url = "http://employee-service/emp/name/"+name;
		List<Employee> employees = tmp.getForObject(url, List.class);
		if(employees.size()==0) {
			return new ResponseEntity("Employee Not Available With Name: "+name, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(employees, HttpStatus.OK);
	}
	@PostMapping("/employee/add")
	public ResponseEntity addEmployee(@RequestBody Employee employee) {
		String url = "http://employee-service/emp/add";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(employee, headers);
		ResponseEntity<Employee> result = tmp.exchange(url, HttpMethod.POST, httpEntity, Employee.class);
		if(result.getBody().getEmpNo()==0) {
			return new ResponseEntity("Employee Already Present With Id: "+employee.getEmpNo(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(result.getBody(), HttpStatus.OK);
	}
	@PutMapping("/employee/update")
	public ResponseEntity updateEmployee(@RequestBody Employee employee) {
		String url = "http://employee-service/emp/update";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Employee> httpEntity = new HttpEntity<Employee>(employee, headers);
		ResponseEntity<Employee> result = tmp.exchange(url, HttpMethod.PUT, httpEntity, Employee.class);
		if(result.getBody().getEmpNo()==0) {
			return new ResponseEntity("Can't Update. Employee not Present With id: "+employee.getEmpNo(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(result.getBody(), HttpStatus.OK);
	}
	@DeleteMapping("employee/delete/{eid}")
	public ResponseEntity deleteEmployeeById(@PathVariable("eid") int id) {
		String url = "http://employee-service/emp/delete/"+id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> result = tmp.exchange(url, HttpMethod.DELETE, null, String.class);
		if(result.getBody().equalsIgnoreCase("deleted")) {
			return new ResponseEntity("Employee Deleted Successfully", HttpStatus.OK);
		}
		return new ResponseEntity(result.getBody(), HttpStatus.NOT_FOUND);
	}
	@GetMapping("/getAssets/all")
	public ResponseEntity getAllAsset(){
		String url = "http://asset-service/asset/all";
		List<Asset> assets = tmp.getForObject(url, List.class);
		if(assets.size()==0) {
			return new ResponseEntity("Assets Not Available", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(assets, HttpStatus.OK);
	}
	@GetMapping("/getAssetBy/id/{eid}")
	public ResponseEntity getAssetById(@PathVariable("eid") int id) {
		String url = "http://asset-service/asset/id/"+id;
		Asset asset =  tmp.getForObject(url, Asset.class);
		if(asset.getAssetId()==0) {
			return new ResponseEntity("Asset Not Available With Id: "+id, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(asset, HttpStatus.OK);
	}
	@GetMapping("/getAssetBy/name/{aname}")
	public ResponseEntity getAssetByName(@PathVariable("aname") String name) {
		String url = "http://asset-service/asset/name/"+name;
		Asset asset = tmp.getForObject(url, Asset.class);
		if(asset.getAssetId()==0) {
			return new ResponseEntity("Asset Not Available With Name: "+name, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(asset, HttpStatus.OK);
	}
	@PostMapping("/asset/add")
	public ResponseEntity addAsset(@RequestBody Asset asset){
		String url = "http://asset-service/asset/add";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Asset> httpEntity = new HttpEntity<Asset>(asset, headers);
		ResponseEntity<Asset> result = tmp.exchange(url, HttpMethod.POST, httpEntity, Asset.class);
		if(result.getBody().getAssetId()==0) {
			return new ResponseEntity("Can't Add. Asset Already Present With Id: "+asset.getAssetId(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(result.getBody(), HttpStatus.OK);
	}
	@PutMapping("/asset/update")
	public ResponseEntity updateAsset(@RequestBody Asset asset) {
		String url = "http://asset-service/asset/update";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Asset> httpEntity = new HttpEntity<Asset>(asset, headers);
		ResponseEntity<Asset> result = tmp.exchange(url, HttpMethod.PUT, httpEntity, Asset.class);
		if(result.getBody().getAssetId()==0) {
			return new ResponseEntity("Can't Update. Asset not Present With id: "+asset.getAssetId(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(result.getBody(), HttpStatus.OK);
	}
	@DeleteMapping("asset/delete/{aid}")
	public ResponseEntity deleteAssetById(@PathVariable("aid") int id) {
		String url = "http://asset-service/asset/delete/"+id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		ResponseEntity<String> result = tmp.exchange(url, HttpMethod.DELETE, null, String.class);
		if(result.getBody().equalsIgnoreCase("deleted")) {
			return new ResponseEntity("Asset Deleted Successfully", HttpStatus.OK);
		}
		return new ResponseEntity(result.getBody(), HttpStatus.NOT_FOUND);
	}
	@GetMapping("getAllocations/all")
	public ResponseEntity getAllAllocations(){
		String url = "http://allocation-service/allocation/all";
		List<AssetAllocation> allocations = tmp.getForObject(url, List.class);
		if(allocations.size()==0) {
			return new ResponseEntity("Allocations Not Available", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(allocations, HttpStatus.OK);
	}
	@PostMapping("allocation/add/{index}")
	public ResponseEntity addAllocation(@PathVariable("index") int i) {
		AssetForm[] forms = tmp.getForObject("http://assetform-service/form/all", AssetForm[].class);
		List<AssetForm> formList  = Arrays.asList(forms);
		if(i>=formList.size()) {
			return new ResponseEntity("Request not found with index: "+i, HttpStatus.NOT_FOUND);
		}
		AssetForm form = formList.get(i);
		if(form.getStatus().equalsIgnoreCase("Allocated")||form.getStatus().equalsIgnoreCase("Rejected")) {
			return new ResponseEntity("The request is already "+form.getStatus(), HttpStatus.BAD_REQUEST);
		}
		Asset asset = tmp.getForObject("http://asset-service/asset/name/"+form.getAssetName(), Asset.class);
		if(asset.getStatus().equalsIgnoreCase("unavailable")) {
			return new ResponseEntity("Asset Not Available", HttpStatus.NOT_FOUND);
		}
		AssetAllocation allocation = new AssetAllocation(0, asset.getAssetId(), form.getEmpId(), LocalDate.now(), form.getReleaseDate());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssetAllocation> allocationEntity = new HttpEntity<AssetAllocation>(allocation, headers);
		ResponseEntity<AssetAllocation> result = tmp.exchange("http://allocation-service/allocation/add", HttpMethod.POST, allocationEntity, AssetAllocation.class);
		HttpEntity<AssetForm> requestEntity = new HttpEntity<AssetForm>(form, headers);
		tmp.exchange("http://assetform-service/form/allocate", HttpMethod.PUT, requestEntity, String.class);
		asset.setQuantity(asset.getQuantity()-1);
		HttpEntity<Asset> assetEntity = new HttpEntity<Asset>(asset, headers);
		tmp.exchange("http://asset-service/asset/update", HttpMethod.PUT, assetEntity, Asset.class);
		return new ResponseEntity(result.getBody(), HttpStatus.OK);
	}
	@GetMapping("getRequests/all")
	public ResponseEntity getAllForms(){
		String url = "http://assetform-service/form/all";
		List<AssetForm> forms = tmp.getForObject(url, List.class);
		if(forms.size()==0) {
			return new ResponseEntity("Requests Not Available", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(forms, HttpStatus.OK);
	}
	@GetMapping("request/status/{fid}")
	public ResponseEntity checkStatus(@PathVariable("fid") int id) {
		String result =  tmp.getForObject("http://assetform-service/form/status/"+id, String.class);
		if(result.equalsIgnoreCase("pending")||result.equalsIgnoreCase("allocated")||result.equalsIgnoreCase("rejected")) {
			return new ResponseEntity("Status: "+result, HttpStatus.OK);
		}
		return new ResponseEntity(result, HttpStatus.NOT_FOUND);
	}
	@PostMapping("/request/add")
	public ResponseEntity addRequest(@RequestBody AssetForm assetForm){
		String url = "http://assetform-service/form/add";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssetForm> httpEntity = new HttpEntity<AssetForm>(assetForm, headers);
		ResponseEntity<AssetForm> result = tmp.exchange(url, HttpMethod.POST, httpEntity, AssetForm.class);
		if(result.getBody().getRequestId()==0) {
			return new ResponseEntity("Can't Add. Request Already Present With Id: "+assetForm.getRequestId(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity(result.getBody(), HttpStatus.OK);
	}
	@PutMapping("request/reject/{index}")
	public ResponseEntity rejectForm(@PathVariable("index") int i) {
		AssetForm[] requests = tmp.getForObject("http://assetform-service/form/all", AssetForm[].class);
		List<AssetForm> formList = Arrays.asList(requests);
		if(i>=formList.size()) {
			return new ResponseEntity("Request not found with index: "+i, HttpStatus.NOT_FOUND);
		}
		AssetForm request = formList.get(i);
		if(request.getStatus().equalsIgnoreCase("Allocated")||request.getStatus().equalsIgnoreCase("Rejected")) {
			return new ResponseEntity("The request is already "+request.getStatus(), HttpStatus.BAD_REQUEST);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<AssetForm> httpEntity = new HttpEntity<AssetForm>(request, headers);
		ResponseEntity<String> result = tmp.exchange("http://assetform-service/form/reject", HttpMethod.PUT, httpEntity, String.class);
		if(result.getBody().equalsIgnoreCase("Rejected")) {
			return new ResponseEntity("Request Rejected Successfully", HttpStatus.OK);
		}
		return new ResponseEntity(result.getBody(), HttpStatus.NOT_FOUND);
	}
	
}
