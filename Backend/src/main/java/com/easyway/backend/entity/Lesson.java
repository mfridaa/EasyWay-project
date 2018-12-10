package com.easyway.backend.entity;

//import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.easyway.backend.entity.security.User;
import com.easyway.backend.utilities.Day;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="Lesson")
public class Lesson {
	@com.fasterxml.jackson.annotation.JsonIgnore
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "day")
	private Day day;
	
	@Column(name = "start")
	private String start;
	
	@Column(name = "end")
	private String end;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(fetch=FetchType.EAGER)
	private Room room;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="teacher_id")
	private Teacher teacher;

	@ManyToMany(
			cascade = {
					CascadeType.PERSIST,
					CascadeType.MERGE
			})
	@JoinTable(name = "week_lesson",
			joinColumns = { @JoinColumn(name = "week_id") },
			inverseJoinColumns = { @JoinColumn(name = "lesson_id") })
    private Set<Week> weeks = new HashSet<>();



	@ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            })
    @JoinTable(name = "user_lesson",
            joinColumns = { @JoinColumn(name = "lesson_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> users = new HashSet<>();
	
	public Lesson() {}
	
	public Lesson(Day day, String start, String end, String name) {
		super();
		this.day = day;
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

	public Set<Week> getWeeks() {
		return weeks;
	}

	public void setWeeks(Set<Week> weeks) {
		this.weeks = weeks;
	}

	@JsonIgnore
	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		users.add(user);
	}


	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Lesson) {
			Lesson o = (Lesson) obj;
			if(o.getId() == this.getId()){
				return true;
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		return id.intValue();
	}
}
