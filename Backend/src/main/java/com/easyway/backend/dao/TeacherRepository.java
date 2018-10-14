package com.easyway.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.easyway.backend.entity.Teacher;

public interface TeacherRepository extends CrudRepository<Teacher, Long> {
	Optional<Teacher> findByName(String name);
	
	
}