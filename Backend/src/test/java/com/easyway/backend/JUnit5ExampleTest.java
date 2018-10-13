package com.easyway.backend;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.easyway.backend.entity.Lesson;

@RunWith(SpringRunner.class)
@DataJpaTest
@EnableAutoConfiguration
@AutoConfigureTestEntityManager 
class JUnit5ExampleTest {
 

	@Autowired
	private TestEntityManager entityManager;
	
	private Lesson lesson;
	private Timestamp begin;
	private Timestamp end;
	private String name;
	
    @Test
    void justAnExample() {
        System.out.println("This test method should be run");
    }
    
    @Before
	public void setUp() {
		lesson = new Lesson(new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()+100), "Online");
	}

	@Test
	public void saveSocialMediaSiteFacebook() {
		Lesson savedFacebookData = this.entityManager.persistAndFlush(lesson);
		assertThat(savedFacebookData.getName()).isEqualTo("Online");

	}
}
