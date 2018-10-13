package com.easyway.backend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Room")
public class Room {
	
	@Id
	private int id;
	
	@Column(name = "name")
	private String name;

	public Room() {
		
	}
	
	public Room(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	@ManyToOne(fetch=FetchType.LAZY)
	private Building building;
	
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
