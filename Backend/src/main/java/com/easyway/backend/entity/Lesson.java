package com.easyway.backend.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="lesson")
public class Lesson {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "start")
	private Timestamp start;
	
	@Column(name = "end")
	private Timestamp end;
	
	@Column(name = "name")
	private String name;
	
	public Lesson(Timestamp start, Timestamp end, String name) {
		super();
		this.start = start;
		this.end = end;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
	
	
}
