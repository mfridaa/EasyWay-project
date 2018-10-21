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

import com.easyway.backend.utilities.Day;

@Entity
@Table(name="Lesson")
public class Lesson {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "day")
	private Day day;
	
	@Column(name = "start")
	private String start;
	
	@Column(name = "end")
	private String end;
	
	@Column(name = "weeks")
	private String weeks;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Room room;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Teacher teacher;
	
	public Lesson() {}
	
	public Lesson(Day day, String start, String end, String name, String weeks) {
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

	public Day getDay() {
		return day;
	}

	public void setDay(Day day) {
		this.day = day;
	}

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}
}
