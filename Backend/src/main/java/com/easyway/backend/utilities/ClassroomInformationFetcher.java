package com.easyway.backend.utilities;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.easyway.backend.dao.BuildingRepository;
import com.easyway.backend.dao.LessonRepository;
import com.easyway.backend.dao.RoomRepository;
import com.easyway.backend.dao.TeacherRepository;
import com.easyway.backend.dao.WeekRepository;
import com.easyway.backend.entity.Building;
import com.easyway.backend.entity.Lesson;
import com.easyway.backend.entity.Room;
import com.easyway.backend.entity.Teacher;
import com.easyway.backend.entity.Week;


@Component
public class ClassroomInformationFetcher {
	//TODO ütemező


	private final BuildingUtility buildingUtility = new BuildingUtility();


	@Autowired
	BuildingRepository buildingRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	@Autowired
	WeekRepository weekRepository;
	
	private static final Logger logger = LogManager.getLogger();
	
	private String classroomActivityInfoUrl = "http://to.ttk.elte.hu/test.php";
	private String classroomNameUrl = "http://to.ttk.elte.hu/teremfoglaltsagi-adatok";
	


	//private static final int TENMINUTEINTERVAL =  1000 * 60 * 10;
	
	private static final int WEEKINTERVAl =  1000 * 60 * 60 * 24 * 7;
	
	//public String resultString;
	
	/*private ArrayList<NameValuePair> requestParams = new ArrayList<NameValuePair>() {{
	    add(new NameValuePair("melyik", "terem_del"));
	    add(new NameValuePair("felev", "2018-2019-1"));
	    add(new NameValuePair("limit", "1"));
	    add(new NameValuePair("teremazon", "511"));
	}};*/

	@Scheduled(fixedRate = WEEKINTERVAl)
	public void fetchNewDataAndSaveToDatabase() {
		String nameUrlPostResult = fetchData(classroomNameUrl, null);
		//resultString = nameUrlPostResult;
		processClassroomString(nameUrlPostResult);
		logger.info("Fetching done");
	}
	
	private void processClassroomString(String classroomString){

		//classroomStream.
		try{
			Document document = Jsoup.parse(classroomString);
			getClassRoomsAndSaveToDatabase(buildingUtility.NORTBUILDING, document);
			getClassRoomsAndSaveToDatabase(buildingUtility.SOUTHBILDING, document);
			getClassRoomsAndSaveToDatabase(buildingUtility.CHEMICBUILDING, document);
		}catch (Exception e){
			logger.error(e.getMessage());
		}

	}
	
	private void getClassRoomsAndSaveToDatabase(String buildingString, Document document){
		Element classRooms = document.getElementById(buildingString);
		Building building = new Building(buildingUtility.getNameOfBuilding(buildingString));
		
		try{
			buildingRepository.save(building);
		}catch (Exception exception){
			building = buildingRepository.findByName(buildingUtility.getNameOfBuilding(buildingString)).get();
		}
		final Building finalBuilding = building;
		Stream<Room> lessonStream = classRooms.children().stream().map(element -> {
			Room roomToAppend = new Room(Integer.parseInt(element.val()), element.text());

			roomToAppend.setBuilding(finalBuilding);

			return roomToAppend;});

	    for(Element classRoom : classRooms.children()){
	    		Room roomToAppend = new Room(Integer.parseInt(classRoom.val()), classRoom.text());
	    		roomToAppend.setBuilding(building);

	    		roomRepository.save(roomToAppend);
	    		building.addClassroom(roomToAppend);
	    		//saveLessonData(getRequestParams(getRoomNameOfBuilding(buildingString), classRoom.val()),roomToAppend);
			//buildingUtility.getRequestParamsFor(buildingUtility.getRoomNameOfBuilding(buildingString), classRoom.val()).forEach(nameValuePair -> {logger.info(nameValuePair.getName() + " "+ nameValuePair.getValue());});
	    		String lessonData = fetchData(
	    				classroomActivityInfoUrl,
						(buildingUtility.getRequestParamsFor(
								buildingUtility.getRoomNameOfBuilding(buildingString),
								classRoom.val())
						)
				);
	    		saveLessons(lessonData, roomToAppend);
	    }

		//roomRepository.saveAll();
	}
	
	
	
	

	/*private void saveLessonData( List<NameValuePair> lessonData, Room roomData) {
		class Saver implements Runnable{
			private List<NameValuePair> lessonData;
			private Room roomData;
			public Saver(List<NameValuePair> lessonData, Room roomData) {
				this.lessonData = lessonData;
				this.roomData = roomData;
			}
			
			@Override
			public void run() {
				String fetchedLessonData = fetchData(classroomActivityInfoUrl, lessonData);
				saveLessons(fetchedLessonData, roomData);
			}
			
		}
		Thread thread = new Thread(new Saver(lessonData, roomData));
		thread.start();
		
	}*/

	
	private void saveLessons(String data, Room room) {

		try{

			Document document = Jsoup.parse(data);
			Elements rows = document.select("tr");

			for(Element row : rows) {
				Elements columns = row.select("td");

				if(columns.size() > 3 && columns.get(3).text().split(" ").length == 2) {

					Teacher teacher = makeTeacher(columns.get(8).text(), columns.get(9).text());
					try {
						Day day = parseDay(columns.get(3).text().split(" ")[0]);
						String interval = columns.get(3).text().split(" ")[1];
						Lesson lesson = new Lesson(day, interval.split("-")[0], interval.split("-")[1], columns.get(1).text());

						lesson.setRoom(room);
						lesson.setTeacher(teacher);
						lessonRepository.save(lesson);
						addWeeks(columns.get(4).text(), lesson);
						teacher.addLesson(lesson);
						room.addLesson(lesson);
					} catch(Exception e) {
						logger.error(e.getMessage());
					}
				}
			}

		}catch (Exception e){
			logger.error(e.getMessage());
		}



	}
	
	private void addWeeks(String weeks, Lesson lesson) {
		String[] weekArray = weeks.split(",");
		for(int i = 0; i < weekArray.length; ++i) {
			Optional<Week> week = weekRepository.findByNumber(Long.parseLong(weekArray[i]));
			if(week.isPresent()) {
				lesson.getWeeks().add(week.get());
				week.get().getLessons().add(lesson);

			}
			else {
				Week weekb = new Week(Long.parseLong(weekArray[i]));
				weekRepository.save(weekb);
				lesson.getWeeks().add(weekb);
				weekb.getLessons().add(lesson);
				weekRepository.save(weekb);
			}
		}
		lessonRepository.save(lesson);
	}
	
	private Day parseDay(String day) {
		Day result = null;
		switch(day) {
		   case "Hétfo" :
			   result = Day.MONDAY;
			   break;
		   case "Kedd" :
			   result = Day.TUESDAY;
			   break;
		   case "Szerda" :
			   result = Day.WEDNESDAY;
			   break; 
		   case "Csütörtök" :
			   result = Day.THURSDAY;
			   break; 
		   case "Péntek" :
			   result = Day.FRIDAY;
			   break; 
		   case "Szombat" :
			   result = Day.SATURDAY;
			   break; 
		   default : result = Day.SUNDAY;
		
		}
		return result;
	}
	
	private Teacher makeTeacher(String nameA, String nameB) {
		Teacher teacher = null;
		nameA = nameA.split(",")[0];
		nameB = nameB.split(",")[0];
		
		if(teacherRepository.findByName(nameA).isPresent()) {
			return teacherRepository.findByName(nameA).get();
		}
		else if(teacherRepository.findByName(nameB).isPresent()) {
			return teacherRepository.findByName(nameB).get();
		}
		else if(!nameA.equals("")) {
			teacher = new Teacher(nameA);
			teacherRepository.save(teacher);
			return teacher;
		}
		else if(!nameB.equals("")) {
			teacher = new Teacher(nameB);
			teacherRepository.save(teacher);
			return teacher;
		}
		else if(teacherRepository.findByName("Nobody").isPresent()) {
			return teacherRepository.findByName("Nobody").get();
		}
		else {
			teacher = new Teacher("Nobody");
			teacherRepository.save(teacher);
			return teacher;
		}
		
	}
	
	private String fetchData(String pageUrl, @Nullable List<NameValuePair> requestParams) {
		String result = null;
		HttpClient client = new HttpClient();
		PostMethod postMethod = new PostMethod(pageUrl);
		try {
			if (requestParams != null){
				NameValuePair[] params = new NameValuePair[requestParams.size()];
				for (int i = 0; i < requestParams.size(); i++) {
					params[i] = requestParams.get(i);
				}
				postMethod.addParameters(params);
			}
			client.executeMethod(postMethod);


			InputStream inputStream = postMethod.getResponseBodyAsStream();
			byte[] buffer = IOUtils.toByteArray(inputStream);
			result = new String(buffer);

		} catch (Exception e) {
			logger.error(e);
		} finally {
			postMethod.releaseConnection();
		}
		//logger.info("Answer from " + pageUrl + " recived (" + result.length() +" characters)");
		return result;
	}
	
	


	
	
}
