package com.easyway.backend.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
public class Lesson {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Timestamp start;
	private Timestamp end;
	private String name;
	
	
}
