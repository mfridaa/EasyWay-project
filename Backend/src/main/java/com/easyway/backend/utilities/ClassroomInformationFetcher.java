package com.easyway.backend.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.easyway.backend.Test;
import com.easyway.backend.dao.BuildingRepository;
import com.easyway.backend.dao.RoomRepository;
import com.easyway.backend.entity.Building;
import com.easyway.backend.entity.Room;

@Component
public class ClassroomInformationFetcher {
	
	@Autowired
	BuildingRepository buildingRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
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
		processClassroomStrin(nameUrlPostResult);

	}
	
	private void processClassroomStrin(String classroomString){
		Document document = Jsoup.parse(classroomString);
		getClassRoomsAndSaveToDatabase(NORTBUILDING,document);
		getClassRoomsAndSaveToDatabase(SOUTHBILDING,document);
		getClassRoomsAndSaveToDatabase(CHEMICBUILDING,document);
	}
	
	private void getClassRoomsAndSaveToDatabase(String buildingString, Document document){
		Element classRooms = document.getElementById(buildingString);
		Building building = new Building(gateNameOfBuilding(buildingString));

	    for(Element classRoom : classRooms.children()){
	    		Room roomToAppend = new Room(Integer.parseInt(classRoom.val()), classRoom.text());
	    		roomToAppend.setBuilding(building);
	    		roomRepository.save(roomToAppend);
	    		building.addClassroom(roomToAppend);
	    }
	    buildingRepository.save(building);
		
	}
	
	private String fetchData(String pageUrl,@Nullable List<NameValuePair> requestParams) {
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
		logger.info("Answer from " + pageUrl + " recived (" + result.length() +" characters)");
		return result;
	}
	
	
	private String gateNameOfBuilding(String building) {
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
	
}
