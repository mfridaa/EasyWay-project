package com.easyway.backand.entitytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LessonTest {
	
	
	@Test
	public void contextLoads() {
	}
	
	/*
	@Autowired
	private TestEntityManager entityManager;
	
	
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	
	private Lesson lesson;
	
	@Before
	public void setUp() {
		Long now = System.currentTimeMillis();
		lesson = new Lesson(new Timestamp(now),new Timestamp(now + 100) , "Sowftware technologies");
	}
	
	@Test
	public void saveLesson() {
		Lesson savedLesson = this.entityManager.persistAndFlush(lesson);
		assertThat(savedLesson.getName()).isEqualTo("Sowftware technologies");
	}
	*/
	
}
