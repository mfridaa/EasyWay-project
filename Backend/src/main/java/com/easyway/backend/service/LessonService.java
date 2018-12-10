package com.easyway.backend.service;


import com.easyway.backend.dao.LessonRepository;
import com.easyway.backend.entity.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
public class LessonService {

    @Autowired
    LessonRepository lessonRepository;






    @PreAuthorize("hasAuthority('LESSON_READ')")
    public Lesson findLessonNamed(String name,long index){
        PageRequest pg = PageRequest.of((int)index,1);
        return lessonRepository.findByNameContaining(name,pg).getContent().get(0);
    }

    @PreAuthorize("hasAuthority('LESSON_READ')")
    public int getNumberOfLessonNamedResult(String name){
        PageRequest pg = PageRequest.of(0,1);
        return lessonRepository.findByNameContaining(name,pg).getTotalPages();
    }





}

