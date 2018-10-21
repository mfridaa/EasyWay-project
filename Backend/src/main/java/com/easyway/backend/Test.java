package com.easyway.backend;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.backend.dao.LessonRepository;
import com.easyway.backend.entity.Lesson;
import com.easyway.backend.utilities.ClassroomInformationFetcher;

@RestController
public class Test {
	
	@Autowired
	ClassroomInformationFetcher fetcher;
	@Autowired
	LessonRepository lessons;
	
	@RequestMapping("test")
	public String test(){
		fetcher.fetchNewDataAndSaveToDatabase();
		return fetcher.resultString;
		
	}
	
	@RequestMapping("testlesson")
	public List<Lesson> testlesson(){
		return (List<Lesson>) lessons.findAll();
		
	}
}
