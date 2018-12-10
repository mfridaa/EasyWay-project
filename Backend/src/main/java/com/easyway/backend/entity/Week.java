package com.easyway.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Week {

	@Id
	@Column(name = "number")
	private Long number;
	
	@ManyToMany(fetch = FetchType.EAGER,mappedBy = "weeks")
    private Set<Lesson> lessons = new HashSet<>();

	public Week() {}
	
	public Week(Long number) {
		super();
		this.number = number;
	}

	public Long getWeekNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	@JsonIgnore
	public Set<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(Set<Lesson> lessons) {
		this.lessons = lessons;
	}
}
