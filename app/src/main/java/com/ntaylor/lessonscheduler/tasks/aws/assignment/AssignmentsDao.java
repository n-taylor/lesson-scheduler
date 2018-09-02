package com.ntaylor.lessonscheduler.tasks.aws.assignment;

import com.google.gson.JsonObject;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.tasks.aws.util.Parser;
import com.ntaylor.lessonscheduler.tasks.aws.util.RestClient;
import com.ntaylor.lessonscheduler.util.Constants;

import java.util.List;

public class AssignmentsDao {

    private static final String FUTURE_ASSIGNMENTS = "/assignment/future";
    private static final String PAST_ASSIGNMENTS = "/assignment/past";
    private static final String GET_ASSIGNMENT_CLASS_DATE = "/assignment/";
    private static final String UPDATE_ASSIGNMENT = "/assignment";
    private static final String POST_ASSIGNMENT = "/assignment";
    private static final String DELETE_ASSIGNMENT = "/assignment/";

    public List<Assignment> getFutureAssignmentByOrg(String orgId, String date){
        String uri = Constants.SERVER_HOST + FUTURE_ASSIGNMENTS;

        String json = stringify(orgId, date);

        String response = RestClient.getPostResponse(uri, json);

        return Parser.parseAssignments(response);
    }

    public List<Assignment> getPastAssignmentsByOrg(String orgId, String date, int limit){
        String uri = Constants.SERVER_HOST + PAST_ASSIGNMENTS;

        String json = stringify(orgId, date, limit);

        String response = RestClient.getPostResponse(uri, json);

        return Parser.parseAssignments(response);
    }

    public List<Assignment> getClassAssignmentByDate(String orgId, String classId, String date){
        String uri = Constants.SERVER_HOST + GET_ASSIGNMENT_CLASS_DATE + orgId + "/" + classId + "/" + date;

        return Parser.parseAssignments(RestClient.getGetResponse(uri));
    }

    public void update(Assignment assignment){
        String uri = Constants.SERVER_HOST + UPDATE_ASSIGNMENT;

        RestClient.getPutResponse(uri, stringify(assignment));
    }

    public void insert(Assignment assignment){
        String uri = Constants.SERVER_HOST + POST_ASSIGNMENT;

        String body = stringify(assignment);

        String result = RestClient.getPostResponse(uri, body);
    }

    public boolean delete(int index){
        String uri = Constants.SERVER_HOST + DELETE_ASSIGNMENT + index;

        String result = RestClient.getPutResponse(uri, "{}");

        return Parser.isSuccess(result);
    }

    // private methods ==================================================================

    private String stringify(Assignment assignment){
        JsonObject top = new JsonObject();
        top.addProperty("index", assignment.getIndex());
        top.addProperty("date", assignment.getDate());
        top.addProperty("org_id", assignment.getOrgId());
        top.addProperty("class_id", assignment.getClassId());
        top.addProperty("teacher_id", assignment.getTeacherId());

        return top.toString();
    }

    private String stringify(String orgId, String date){
        JsonObject top = new JsonObject();
        top.addProperty("org_id", orgId);
        top.addProperty("date", date);

        return top.toString();
    }

    private String stringify(String orgId, String date, int limit){
        JsonObject top = new JsonObject();
        top.addProperty("org_id", orgId);
        top.addProperty("date", date);
        top.addProperty("limit", limit);

        return top.toString();
    }

}
