package com.easyway.backend.utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ClassroomInformationFetcher {
	
	private static final Logger logger = LogManager.getLogger();
	
	private String classroomActivityInfoUrl = "http://to.ttk.elte.hu/test.php";
	private String classroomNameUrl = "http://to.ttk.elte.hu/teremfoglaltsagi-adatok";
	
	private ArrayList<NameValuePair> requestParams = new ArrayList<NameValuePair>() {{
	    add(new NameValuePair("melyik", "terem_del"));
	    add(new NameValuePair("felev", "2018-2019-1"));
	    add(new NameValuePair("limit", "1"));
	    add(new NameValuePair("teremazon", "511"));
	}};
	
	public void fetchNewDataAndSaveToDatabase() {
		String nameUrlPostResult = fetchData(classroomNameUrl, null);
		List<NameValuePair> roomDetails = parseClassroomStrin(nameUrlPostResult);
		for(NameValuePair pair : roomDetails) {
			logger.info(pair.getName() + " " + pair.getValue());
		}
		
	}
	
	private List<NameValuePair> parseClassroomStrin(String classroomString){
		return new ArrayList<>();
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
			postMethod.getResponseBodyAsStream();
			result = postMethod.getResponseBodyAsString();
		} catch (Exception e) {
			logger.error(e);
		} finally {
			postMethod.releaseConnection();
		}
		logger.info("Answer from " + pageUrl + " recived (" + result.length() +" characters)");
		return result;
	}
	
}
