package com.cg.entity;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Asset {
	private Integer assetId;
	@NotNull
	@Size(min=3, message = "Asset name should be of atleast 3 characters")
	private String assetName;
	@NotNull
	@Size(min=2, message = "Description should be of atleast 2 characters")
	private String description;
	@NotNull
	private Integer quantity;
	@NotEmpty
	private String status;
	
	public Asset(Integer assetId, String assetName, String description, Integer quantity, String status) {
		super();
		this.assetId = assetId;
		this.assetName = assetName;
		this.description = description;
		this.quantity = quantity;
		this.status = status;
	}
	
	public Asset() {
		super();
	}

	public Integer getAssetId() {
		return assetId;
	}
	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
