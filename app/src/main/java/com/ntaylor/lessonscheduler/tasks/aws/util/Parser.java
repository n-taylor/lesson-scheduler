package com.ntaylor.lessonscheduler.tasks.aws.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    /**
     * Parses the JSON to form a user object
     * @param json The JSON object containing user info
     * @return The User object if successful, null in the case of errors
     */
    public static User parseSingleUser(String json){
        try {
            JsonElement elemTop = new JsonParser().parse(json);
            if (elemTop != null){
                JsonObject top = elemTop.getAsJsonObject();

                JsonElement userName = top.get("user_name");
                JsonElement userId = top.get("user_id");

                if (userName != null && userId != null){
                    String org = (top.get("org_id") != null) ? top.get("org_id").getAsString() : "";

                    User user = new User(userName.getAsString(), org);

                    user.setUserId(userId.getAsString());
                    return user;

                }
            }
        }
        catch (Exception ex){
            return null;
        }
        return null;
    }

    public static Assignment parseAssignment(JsonElement element){
        if (element != null) {
            JsonObject top = element.getAsJsonObject();

            JsonElement eDate = top.get("date");
            JsonElement eOrgId = top.get("org_id");
            JsonElement eClassId = top.get("class_id");
            JsonElement eTeacherId = top.get("teacher_id");

            if (eDate != null && eOrgId != null && eClassId != null && eTeacherId != null){
                String date = eDate.getAsString();
                String orgId = eOrgId.getAsString();
                String classId = eClassId.getAsString();
                String teacherId = eTeacherId.getAsString();

                return new Assignment(date, orgId, classId, teacherId);
            }
        }
        return null;
    }

    /**
     * Parses an organization from a JSON object
     * @param json
     * @return Null if an error occurred, the parsed organization otherwise
     */
    public static Organization parseOrg(String json){
        try {
            JsonElement elemTop = new JsonParser().parse(json);
            if (elemTop != null){
                JsonObject top = elemTop.getAsJsonObject();

                JsonElement orgId = top.get("org_id");
                JsonElement orgName = top.get("org_name");

                if (orgId != null && orgName != null){
                    Organization org = new Organization(orgName.getAsString());
                    org.setOrgId(orgId.getAsString());
                    return org;
                }
            }
        }
        catch (Exception ex){
            return null;
        }
        return null;
    }

    /**
     * Parses the JSON string into a list of users
     */
    public static List<User> parseUsers(String json){
        List<User> usersList = new ArrayList<>();
        JsonElement elemTop = new JsonParser().parse(json);

        if (elemTop != null){
            JsonArray users = elemTop.getAsJsonObject().getAsJsonArray("users");
            if (users != null){
                for (JsonElement user : users){
                    String userJson = user.toString();
                    usersList.add(parseSingleUser(userJson));
                }
            }
        }

        return usersList;
    }

    public static List<Assignment> parseAssignments(String json){
        List<Assignment> assignmentsList = new ArrayList<>();
        JsonElement eTop = new JsonParser().parse(json);

        if (eTop != null){
            JsonArray assignments = eTop.getAsJsonObject().getAsJsonArray("assignments");
            if (assignments != null){
                for (JsonElement assignment : assignments){
                    assignmentsList.add(parseAssignment(assignment));
                }
            }
        }
        return assignmentsList;
    }
}
