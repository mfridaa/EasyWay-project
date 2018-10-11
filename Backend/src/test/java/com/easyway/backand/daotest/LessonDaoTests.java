package com.easyway.backand.daotest;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.easyway.backend.dao.LessonRepository;
import com.easyway.backend.entity.Lesson;

@RunWith(SpringRunner.class)
public class LessonDaoTests {
	
	
	
	@Autowired
	LessonRepository repository;
	
	@Test
	public void should_find_no_lessons_if_repository_is_empty() {
		Iterable<Lesson> lessons = repository.findAll();
 
		assertThat(lessons).isEmpty();
	}
	
}
