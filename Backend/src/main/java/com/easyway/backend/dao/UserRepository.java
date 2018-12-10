package com.easyway.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.easyway.backend.entity.security.User;

public interface UserRepository extends CrudRepository<User, Long>  {
	public Optional<User> findByEmail(String email);
}
