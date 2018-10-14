package com.easyway.backend.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Building")
public class Building {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	public Building() {}
	
	public Building(String name) {
		this.name = name;
	}
	
	public Building(String name, List<Room> classrooms) {
		this.name = name;
		this.classrooms = classrooms;
	}

	@OneToMany(mappedBy="building")
	private List<Room> classrooms = new ArrayList<>();
	
	public void addClassroom(Room classroom) {
		this.classrooms.add(classroom);
		if (classroom.getBuilding() != this) {
			classroom.setBuilding(this);
		}
	}
	
}
