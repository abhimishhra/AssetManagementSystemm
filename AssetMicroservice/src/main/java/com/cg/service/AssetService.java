package com.cg.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.entity.Asset;
import com.cg.exception.AssetAlreadyExistException;
import com.cg.exception.AssetNotFoundException;
import com.cg.repository.AssetRepository;

@Service
public class AssetService {
	@Autowired
	AssetRepository repository;
	
	public List<Asset> getAllAssets(){
		List<Asset> assetList = repository.findAll();
		return assetList;
	}
	public Asset getAssetById(int id) throws AssetNotFoundException{
		Optional<Asset> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			throw new AssetNotFoundException("Asset Not Present With Id: "+id);
		}
	}
	public Asset getAssetByName(String name) throws AssetNotFoundException{
		Optional<Asset> optional = repository.findByAssetName(name);
		if(optional.isPresent()) {
			return optional.get();
		}
		else {
			throw new AssetNotFoundException("Asset Not Present With Name: "+name);
		}
	}
	public Asset addAsset(Asset asset) throws AssetAlreadyExistException {
		int id = asset.getAssetId();
		if(repository.existsById(id)) {
			throw new AssetAlreadyExistException("Asset Already Exists With Id: "+id);
		}
		else {
			return repository.save(asset);
			
		}
	}
	public Asset updateAsset(Asset asset) throws AssetNotFoundException{
		int id = asset.getAssetId();
		if(repository.existsById(id)) {
			if(asset.getQuantity()==0) {
				asset.setStatus("unavailable");
			}
			return repository.save(asset);
		}
		else {
			throw new AssetNotFoundException("Asset Not Present With Id: "+id);
		}
	}
	public Boolean deleteAssetById(int id) throws AssetNotFoundException {
		if(repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		else {
			throw new AssetNotFoundException("Asset Not Present With Id: "+id);
		}
	}
}
