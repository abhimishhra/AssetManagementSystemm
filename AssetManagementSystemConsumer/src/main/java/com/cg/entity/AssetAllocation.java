package com.cg.entity;

import java.time.LocalDate;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

public class AssetAllocation {
	private int allocationId;
	@Positive
	private int assetId;
	@Positive
	private int empNo;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate allocationDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate releaseDate;
	public int getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(int allocationId) {
		this.allocationId = allocationId;
	}
	public int getAssetId() {
		return assetId;
	}
	public void setAssetId(int assetId) {
		this.assetId = assetId;
	}
	public int getEmpNo() {
		return empNo;
	}
	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}
	public LocalDate getAllocationDate() {
		return allocationDate;
	}
	public void setAllocationDate(LocalDate allocationDate) {
		this.allocationDate = allocationDate;
	}
	public LocalDate getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}
	public AssetAllocation(int allocationId, int assetId, int empNo, LocalDate allocationDate, LocalDate releaseDate) {
		super();
		this.allocationId = allocationId;
		this.assetId = assetId;
		this.empNo = empNo;
		this.allocationDate = allocationDate;
		this.releaseDate = releaseDate;
	}
	public AssetAllocation() {
		super();
	}
	
}
