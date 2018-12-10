package com.easyway.backend.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.easyway.backend.dao.UserRepository;
import com.easyway.backend.entity.security.User;
import com.easyway.backend.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @PreAuthorize("hasAuthority('COMPANY_READ') and hasAuthority('DEPARTMENT_READ')")
    public ResponseEntity<User> getUser(@PathVariable long id, Principal principal){
        Optional<User> user = userRepository.findByEmail(principal.getName());

        if(user.isPresent() && user.get().getId() == id){
            return ResponseEntity.ok(user.get());
        }
        else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/registration")
    public ResponseEntity<Void> create(@RequestParam String username, @RequestParam String password) {
    	boolean saved = userDetailsServiceImpl.registerUser(username, password);
    	if(saved)
    		return ResponseEntity.ok().build();
    	else
    		return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
    
    @PostMapping("/addLessonToTimeTable")
    @PreAuthorize("hasAuthority('LESSON_READ')")
    public ResponseEntity<Void> addLesson(@RequestParam Long lessonId, @RequestParam Long userId) {
    	boolean saved = userDetailsServiceImpl.addLesson(lessonId, userId);
    	if(saved)
    		return ResponseEntity.ok().build();
    	else
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    @PostMapping("/deleteLessonFromTimeTable")
    @PreAuthorize("hasAuthority('LESSON_READ')")
    public ResponseEntity<Void> deleteLesson(@RequestParam Long lessonId, @RequestParam Long userId) {
    	boolean saved = userDetailsServiceImpl.deleteLesson(lessonId, userId);
    	if(saved)
    		return ResponseEntity.ok().build();
    	else
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
