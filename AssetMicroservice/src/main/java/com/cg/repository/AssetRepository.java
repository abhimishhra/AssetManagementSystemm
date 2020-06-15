package com.cg.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.Asset;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
	Optional<Asset> findByAssetName(String name);
}
