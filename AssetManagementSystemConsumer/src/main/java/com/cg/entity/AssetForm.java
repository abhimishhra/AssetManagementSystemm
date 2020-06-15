package com.cg.entity;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AssetForm {
	private int requestId;
	@Positive(message = "Manager Id must be positive")
	private int managerId;
	@Positive(message = "Employee Id must be positive")
	private int empId;
	@NotNull
	@Size(min=3, message = "Asset name should be of atleast 3 characters")
	private String assetName;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate releaseDate;
	@NotEmpty
	private String status;
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getManagerId() {
		return managerId;
	}
	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}
	public int getEmpId() {
		return empId;
	}
	public void setEmpId(int empId) {
		this.empId = empId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public AssetForm(int requestId, int managerId, int empId, String assetName, LocalDate releaseDate, String status) {
		super();
		this.requestId = requestId;
		this.managerId = managerId;
		this.empId = empId;
		this.assetName = assetName;
		this.releaseDate = releaseDate;
		this.status = status;
	}
	public AssetForm() {
		super();
	}
	
}