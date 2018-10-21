package com.easyway.backend.entity.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Authority {

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	@Column
	private String name;

	public String getName() {
		return name;
	}
	
	
}
