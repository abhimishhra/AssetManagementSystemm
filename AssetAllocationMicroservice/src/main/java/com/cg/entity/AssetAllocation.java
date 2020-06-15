package com.cg.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="asset_allocation")
public class AssetAllocation {
	@Id
	@Column(name="allocation_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int allocationId;
	@Positive
	@Column(name="asset_id")
	private int assetId;
	@Positive
	@Column(name="emp_no")
	private int empNo;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="allocation_date")
	private LocalDate allocationDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="release_date")
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
