package com.easyway.backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.backend.dao.BuildingRepository;
import com.easyway.backend.dao.LessonRepository;
import com.easyway.backend.entity.Lesson;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {
	
	@Autowired
	private LessonRepository lessonRepository;

	@Autowired
	private BuildingRepository buildingRepository;
	/*
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody
	List<Lesson> getAll(){
		return (List<Lesson>) lessonRepository.findAll();
	}
	*/

	@GetMapping("")
	@Transactional(readOnly = true)
	@PreAuthorize("hasAuthority('LESSON_READ')")
	public List<Lesson> getAll(){
		return new ArrayList<>(lessonRepository.findAll());
	}

	@PutMapping
	@PreAuthorize("hasAuthority('LESSON_EDIT')")
	public ResponseEntity<Void> createNewLesson(@RequestBody Lesson lesson){
		Lesson l = lessonRepository.save(lesson);
		if(l != null){
			return new ResponseEntity(HttpStatus.CREATED);
		}
		else{
			return  new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@GetMapping("/totalnumber")
	@PreAuthorize("hasAuthority('LESSON_READ')")
	public int getNumber(){
		return (int)lessonRepository.count();
	}

	@GetMapping("/{number}")
	@PreAuthorize("hasAuthority('LESSON_READ')")
	public Lesson getFew(@PathVariable long number){
		PageRequest pg = PageRequest.of((int)number,1);
		Page page = lessonRepository.findAllByOrderByNameAsc(pg);
		return (Lesson)page.getContent().get(0);

	}

	@GetMapping("/page/totalnumber")
	@PreAuthorize("hasAuthority('LESSON_READ')")
	public int getFew(){
		PageRequest pg = PageRequest.of(0,1);
		Page page = lessonRepository.findAllByOrderByNameAsc(pg);
		return page.getTotalPages();

	}

}
