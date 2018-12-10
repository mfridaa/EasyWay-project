package com.easyway.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyway.backend.dao.AuthorityRepository;
import com.easyway.backend.dao.LessonRepository;
import com.easyway.backend.dao.UserRepository;
import com.easyway.backend.entity.Lesson;
import com.easyway.backend.entity.security.Authority;
import com.easyway.backend.entity.security.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
//TODO add timetable
//TODO manage lessons (add, delete, update)
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private LessonRepository lessonRepository;
	
	private final static long BASIC_AUTHORITY = 1;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByEmail(username);
		if (user.isPresent()) {
			return user.get();
		}
		throw new UsernameNotFoundException(username);
	}

	public boolean registerUser(String email, String password) {
		User user = new User(email, passwordEncoder().encode(password));
		addBasicAuthority(user);
		return userRepository.save(user) != null ? true : false;
	}

	private void addBasicAuthority(User user) {
		Optional<Authority> auth = authorityRepository.findById(BASIC_AUTHORITY);
		if(auth.isPresent()) {
			auth.get().addUser(user);
			user.addAuthority(auth.get());
		}
			
	}
	
	public boolean addLesson(Long lessonId, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		Optional<Lesson> lesson = lessonRepository.findById(userId);
		
		if(user.isPresent() && lesson.isPresent()) {
			user.get().addLesson(lesson.get());
			lesson.get().addUser(user.get());
			return true;
		}

		return false;
	}
	
	public boolean deleteLesson(Long lessonId, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		Optional<Lesson> lesson = lessonRepository.findById(userId);
		
		if(user.isPresent() && lesson.isPresent()) {
			user.get().getLessons().remove(lesson.get());
			lesson.get().getUsers().remove(user.get());
			return true;
		}

		return false;
	}
}
