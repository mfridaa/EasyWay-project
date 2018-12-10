package com.easyway.backend;

import com.easyway.backend.dao.LessonRepository;
import com.easyway.backend.dao.WeekRepository;
import com.easyway.backend.entity.Lesson;
import com.easyway.backend.entity.Week;
import com.easyway.backend.utilities.ClassroomInformationFetcher;
import com.easyway.backend.utilities.Day;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@SpringBootTest
public class LessonRepositoryIntegrationTest {
    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    WeekRepository weekRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {

        Lesson lesson = new Lesson(Day.MONDAY,"13:00","14:00","Test");

        lessonRepository.save(lesson);

        String[] weekArray = {"1","2","3","4","5"};

        for(int i = 0; i < weekArray.length; ++i) {
            Optional<Week> week = weekRepository.findByNumber(Long.parseLong(weekArray[i]));
            if(week.isPresent()) {
                Week settedWeek = week.get();
                lesson.getWeeks().add(week.get());
                System.out.println("a "+lesson.getWeeks().size());

                settedWeek.getLessons().add(lesson);


            }
            else {
                System.out.println("B start");
                Week weekb = new Week(Long.parseLong(weekArray[i]));
                weekRepository.save(weekb);
                lesson.getWeeks().add(weekb);
                weekb.getLessons().add(lesson);

                System.out.println("b" + lesson.getWeeks().size());
                Lesson recivedLesson = lessonRepository.save(lesson);

                weekRepository.save(weekb);
            }
        }

        Lesson recivedLesson = lessonRepository.save(lesson);


        Lesson lessonFromReqpostory = lessonRepository.findByName("Test").get(0);
        // given

        // then

        assertEquals(lesson.getName(),lessonFromReqpostory.getName());
    }
}
