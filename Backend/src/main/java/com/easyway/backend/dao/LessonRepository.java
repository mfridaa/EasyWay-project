package com.easyway.backend.dao;

import com.easyway.backend.entity.*;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;


public interface LessonRepository extends CrudRepository<Lesson,Long> {
	Collection<Lesson> findAll();
	List<Lesson> findByName(String name);
	Page<Lesson> findAllByOrderByNameAsc(Pageable pagerequest);
	Page<Lesson> findByNameContaining(String name,Pageable pageable);
}
