package com.easyway.backend.entity.security;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Authority {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	@Column
	private String name;
	
	@ManyToMany(fetch = FetchType.EAGER,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "authorities")
    private Set<User> users = new HashSet<>();

	public String getName() {
		return name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
}
