package com.easyway.backend;

import java.sql.Timestamp;

import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;

import com.easyway.backend.entity.Lesson;

@RunWith(SpringRunner.class)
@DataJpaTest
class Asd {
 
	@Autowired
	private TestEntityManager entityManager;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Lesson lesson;

	private String name;

	private Timestamp begin;
	
	private Timestamp end;

	@Before
	public void setUp() {
		name = "lesson";
		begin = new Timestamp(System.currentTimeMillis());
		end = new Timestamp(System.currentTimeMillis()+100);
		lesson = new Lesson(begin, end, "lesson");
		System.out.println("asd");
	}

	@Test
	public void saveSocialMediaSiteFacebook() {
		
		name = "lesson";
		begin = new Timestamp(System.currentTimeMillis());
		end = new Timestamp(System.currentTimeMillis()+100);
		lesson = new Lesson(begin, end, "lesson");
		System.out.println("asd");
		Lesson savedFacebookData = entityManager.persistAndFlush(lesson);
		assertThat(this.lesson.getName()).isEqualTo("lesson");

	}
}