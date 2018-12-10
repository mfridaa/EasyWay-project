package com.easyway.backend.entity;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

	@ManyToOne(fetch=FetchType.EAGER)
	private Building building;
	
	@OneToMany(mappedBy="room")
	private List<Lesson> lessons = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "room_position",
            joinColumns = { @JoinColumn(name = "room_id") },
            inverseJoinColumns = { @JoinColumn(name = "position_id") })
    private Set<Position> positions = new HashSet<>();
	
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


	@JsonIgnore
	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public Set<Position> getPositions() {
		return positions;
	}

	public void setPositions(Set<Position> positions) {
		this.positions = positions;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addPoistion(Position position) {
		positions.add(position);
	}
}
