package com.easyway.backend.utilities;

import org.apache.commons.httpclient.NameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuildingUtility {
    //TODO: Create property file
    public static final String NORTBUILDING = "eszaki";
    public static final String SOUTHBILDING = "deli";
    public static final String CHEMICBUILDING = "kemia";

    private static final String WHERE = "melyik";
    private static final String CLASSROOMID = "teremazon";
    private static final String SEMESTER = "felev";
    private static final String LIMIT = "limit";



    private static final HashMap<String,String> requestParams = new HashMap<String,String>(){{
        put(SEMESTER,"2018-2019-1");
        put(LIMIT,"1000");
    }};

    public static String getNameOfBuilding(String building) {
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

    public static String getRoomNameOfBuilding(String building) {
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

    public static List<NameValuePair> getRequestParamsFor(String buildingName, String withClassroomId) {

        final List<NameValuePair> finalRequestParams = new ArrayList<NameValuePair>(){{
            add(new NameValuePair(WHERE, buildingName));
            requestParams.entrySet().forEach(nameValuePair -> {
                add(new NameValuePair(nameValuePair.getKey(), nameValuePair.getValue()));
                add(new NameValuePair(CLASSROOMID, withClassroomId));
            });
        }};

        return finalRequestParams;
    }
}
