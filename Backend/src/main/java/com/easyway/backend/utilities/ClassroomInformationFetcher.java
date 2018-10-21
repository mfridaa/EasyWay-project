package com.easyway.backend.utilities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.easyway.backend.Test;
import com.easyway.backend.dao.BuildingRepository;
import com.easyway.backend.dao.LessonRepository;
import com.easyway.backend.dao.RoomRepository;
import com.easyway.backend.dao.TeacherRepository;
import com.easyway.backend.entity.Building;
import com.easyway.backend.entity.Lesson;
import com.easyway.backend.entity.Room;
import com.easyway.backend.entity.Teacher;

@Component
public class ClassroomInformationFetcher {
	
	@Autowired
	BuildingRepository buildingRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	TeacherRepository teacherRepository;
	
	@Autowired
	LessonRepository lessonRepository;
	
	private static final Logger logger = LogManager.getLogger();
	
	private String classroomActivityInfoUrl = "http://to.ttk.elte.hu/test.php";
	private String classroomNameUrl = "http://to.ttk.elte.hu/teremfoglaltsagi-adatok";
	
	private final String NORTBUILDING = "eszaki";
	private final String SOUTHBILDING = "deli";
	private final String CHEMICBUILDING = "kemia";
	
	public String resultString;
	
	private ArrayList<NameValuePair> requestParams = new ArrayList<NameValuePair>() {{
	    add(new NameValuePair("melyik", "terem_del"));
	    add(new NameValuePair("felev", "2018-2019-1"));
	    add(new NameValuePair("limit", "1"));
	    add(new NameValuePair("teremazon", "511"));
	}};
	
	public void fetchNewDataAndSaveToDatabase() {
		String nameUrlPostResult = fetchData(classroomNameUrl, null);
		resultString = nameUrlPostResult;
		processClassroomString(nameUrlPostResult);

	}
	
	private void processClassroomString(String classroomString){
		Document document = Jsoup.parse(classroomString);
		getClassRoomsAndSaveToDatabase(NORTBUILDING, document);
		getClassRoomsAndSaveToDatabase(SOUTHBILDING, document);
		getClassRoomsAndSaveToDatabase(CHEMICBUILDING, document);
	}
	
	private void getClassRoomsAndSaveToDatabase(String buildingString, Document document){
		Element classRooms = document.getElementById(buildingString);
		Building building = new Building(getNameOfBuilding(buildingString));

		buildingRepository.save(building);
		
	    for(Element classRoom : classRooms.children()){
	    		Room roomToAppend = new Room(Integer.parseInt(classRoom.val()), classRoom.text());
	    		roomToAppend.setBuilding(building);
	    		roomRepository.save(roomToAppend);
	    		building.addClassroom(roomToAppend);
	    		String lessonData = fetchData(classroomActivityInfoUrl, getRequestParams(getRoomNameOfBuilding(buildingString), classRoom.val()));
	    		saveLessons(lessonData, roomToAppend);
	    }
		
	}
	
	private List<NameValuePair> getRequestParams(String building, String id) {
		List<NameValuePair> requestParams = new ArrayList<>();
		
		requestParams.add(new NameValuePair("melyik", building));
		requestParams.add(new NameValuePair("felev", "2018-2019-1"));
		requestParams.add(new NameValuePair("limit", "1000"));
		requestParams.add(new NameValuePair("teremazon", id));
	    
		return requestParams;
	}
	
	private void saveLessons(String data, Room room) {
		Document document = Jsoup.parse(data);
		Elements rows = document.select("tr");

		for(Element row : rows) {
			Elements columns = row.select("td");

			if(columns.size() > 3 && columns.get(3).text().split(" ").length == 2) {

				Teacher teacher = makeTeacher(columns.get(8).text(), columns.get(9).text());
				try {
					Day day = parseDay(columns.get(3).text().split(" ")[0]);
					String interval = columns.get(3).text().split(" ")[1];
					Lesson lesson = new Lesson(day, interval.split("-")[0], interval.split("-")[1], columns.get(1).text(), 
							columns.get(4).text());
					lesson.setRoom(room);
					lesson.setTeacher(teacher);
					lessonRepository.save(lesson);
					teacher.addLesson(lesson);
					room.addLesson(lesson);
				} catch(Exception e) {

				}


			}
		}
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
		String result = "";
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
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			postMethod.releaseConnection();
		}
		//logger.info("Answer from " + pageUrl + " recived (" + result.length() +" characters)");
		return result;
	}
	
	
	private String getNameOfBuilding(String building) {
		String name = "";
		switch (building){
		case SOUTHBILDING:
			name = "Északi épület";
			break;
		case NORTBUILDING:
			name = "Déli épület";
			break;
		case CHEMICBUILDING:
			name = "Kémia épület";
			break;
		default:
			break;
		}
		return name;
	}
	
	private String getRoomNameOfBuilding(String building) {
		String name = "";
		switch (building){
		case SOUTHBILDING:
			name = "terem_eszak";
			break;
		case NORTBUILDING:
			name = "terem_del";
			break;
		case CHEMICBUILDING:
			name = "terem_kemia";
			break;
		default:
			break;
		}
		return name;
	}
	
}
