package com.easyway.backend.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.easyway.backend.dao.BuildingRepository;
import com.easyway.backend.entity.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
	public List<Building> getAll(){
		Iterable<Building> buildings = buildingRepository.findAll();
		ArrayList<Building> lessonArrayList = new ArrayList<Building>();

		buildings.forEach(lesson -> {lessonArrayList.add(lesson);});
		return lessonArrayList;
	}
}
