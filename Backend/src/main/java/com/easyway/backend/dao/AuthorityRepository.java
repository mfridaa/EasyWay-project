package com.easyway.backend.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.easyway.backend.entity.security.Authority;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {
	Optional<Authority> findById(Long id);
}
