package com.easyway.backend.entity.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


//TODO : add UserDetails implementation!!!
@Entity
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Column(name="email",unique=true)
	private String email;
	
	@Column(name="password")
	private String password;
	

	@ManyToMany(cascade = { 
	        CascadeType.PERSIST, 
	        CascadeType.MERGE
	    },fetch = FetchType.EAGER)
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

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		for (Authority authority : authorities) {
			grantedAuthorities.add(()-> authority.getName());
		}
		return grantedAuthorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}


	
	
}
