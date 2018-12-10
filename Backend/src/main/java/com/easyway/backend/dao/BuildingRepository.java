package com.easyway.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.easyway.backend.entity.Building;

public interface BuildingRepository extends CrudRepository<Building, Long> {
	Optional<Building> findByName(String name);
}
