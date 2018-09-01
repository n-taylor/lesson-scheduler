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
