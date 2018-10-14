package com.easyway.backend.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Room")
public class Room {
	
	@Id
	private int id;
	
	@Column(name = "name")
	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	private Building building;
	
	@OneToMany(mappedBy="room")
	private List<Lesson> lessons = new ArrayList<>();
	
	public Room() {
		
	}
	
	public Room(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public void addLesson(Lesson lesson) {
		this.lessons.add(lesson);
		if (lesson.getRoom() != this) {
			lesson.setRoom(this);
		}
	}
	
	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
}
