package com.easyway.backend.controller;

import com.easyway.backend.dao.UserRepository;
import com.easyway.backend.entity.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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



}
