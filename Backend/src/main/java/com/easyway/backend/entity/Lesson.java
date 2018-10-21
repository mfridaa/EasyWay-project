package com.easyway.backend.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Lesson")
public class Lesson {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "day")
	private String day;
	
	@Column(name = "start")
	private String start;
	
	@Column(name = "end")
	private String end;
	
	@Column(name = "weeks")
	private String weeks;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Room room;
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Teacher teacher;
	
	public Lesson() {}
	
	public Lesson(String day, String start, String end, String name, String weeks) {
		super();
		this.day = day;
		this.start = start;
		this.end = end;
		this.name = name;
		this.weeks = weeks;
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonIgnore
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	@JsonIgnore
	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
}
