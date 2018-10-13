package com.easyway.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.backend.utilities.ClassroomInformationFetcher;

@RestController
public class Test {
	
	@Autowired
	ClassroomInformationFetcher fetcher;
	
	@RequestMapping("test")
	public String test(){
		fetcher.fetchNewDataAndSaveToDatabase();
		return fetcher.resultString;
		
	}
}
