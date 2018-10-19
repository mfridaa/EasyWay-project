package com.easyway.backend.entity.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


//TODO : add UserDetails implementation!!!
@Entity
public class User  {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Column(name="email",unique=true)
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="credentails_expired")
	private boolean creadentialsExpired;

	@ManyToMany(cascade = { 
	        CascadeType.PERSIST, 
	        CascadeType.MERGE
	    })
	    @JoinTable(name = "users_authorities",
	        joinColumns = @JoinColumn(name = "user_id"),
	        inverseJoinColumns = @JoinColumn(name = "auhority_id")
	    )
	private List<Authority> authorities = new ArrayList<>();
	
	public User() {}

	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}


	
	
}
