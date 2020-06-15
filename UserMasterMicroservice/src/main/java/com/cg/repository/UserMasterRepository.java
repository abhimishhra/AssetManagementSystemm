package com.cg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.entity.UserMaster;

public interface UserMasterRepository extends JpaRepository<UserMaster,Integer> {
	Optional<UserMaster> findByUserName(String name);
}
