package com.easyway.backend.dao;

import com.easyway.backend.entity.*;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface LessonRepository extends CrudRepository<Lesson,Long> {
	List<Lesson> findByName(String name);
}
