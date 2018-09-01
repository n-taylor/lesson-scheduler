package com.ntaylor.lessonscheduler.tasks.aws.classroom;

import com.google.gson.JsonObject;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.tasks.aws.util.Parser;
import com.ntaylor.lessonscheduler.tasks.aws.util.RestClient;
import com.ntaylor.lessonscheduler.util.Constants;

import java.sql.ResultSet;
import java.util.List;

public class ClassesDao {

    private static final String CLASSES_BY_ID = "/class/byId/";
    private static final String CLASSES_BY_ORG = "/class/byOrg";
    private static final String INSERT_CLASS = "/class";
    private static final String UPDATE_CLASS = "/class";
    private static final String DELETE_CLASS = "/class/";

    public List<Classroom> getClassesById(String classId){
        String uri = Constants.SERVER_HOST + CLASSES_BY_ID + classId;

        return Parser.parseClasses(RestClient.getGetResponse(uri));
    }

    public List<Classroom> getClassesByOrg(String orgId){
        String uri = Constants.SERVER_HOST + CLASSES_BY_ORG + orgId;

        return Parser.parseClasses(RestClient.getGetResponse(uri));
    }

    public void insertClass(Classroom classroom){
        String uri = Constants.SERVER_HOST + INSERT_CLASS;

        RestClient.getPostResponse(uri, stringify(classroom));
    }

    public void updateClassroom(Classroom classroom){
        String uri = Constants.SERVER_HOST + UPDATE_CLASS;

        RestClient.getPutResponse(uri, stringify(classroom));
    }

    public void deleteClassroom(String classId){
        String uri = Constants.SERVER_HOST + DELETE_CLASS + classId;

        RestClient.getPutResponse(uri, "{}");
    }

    // private methods ==================================================================

    private String stringify(Classroom classroom){
        JsonObject top = new JsonObject();
        top.addProperty("class_id", classroom.getClassId());
        top.addProperty("class_name", classroom.getClassName());
        top.addProperty("org_id", classroom.getOrgId());

        return top.toString();
    }

}
