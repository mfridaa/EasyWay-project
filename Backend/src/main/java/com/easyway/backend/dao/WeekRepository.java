package com.easyway.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.easyway.backend.entity.Week;

public interface WeekRepository extends CrudRepository<Week, Long> {
	Optional<Week> findByNumber(Long number);
}
