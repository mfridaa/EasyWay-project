package com.easyway.backend.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name="Teacher")
public class Teacher {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	
	public Teacher() {}
	
	public Teacher(String name) {
		this.name = name;
	}
	
	@OneToMany(mappedBy="teacher",fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Lesson> lessons = new ArrayList<>();
	
	public void addLesson(Lesson lesson) {
		this.lessons.add(lesson);
		if (lesson.getTeacher() != this) {
			lesson.setTeacher(this);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Teacher) {
			Teacher other = (Teacher)obj;
			return other.name.equals(name);
		}
			
		return false;
	}
	
	
}
