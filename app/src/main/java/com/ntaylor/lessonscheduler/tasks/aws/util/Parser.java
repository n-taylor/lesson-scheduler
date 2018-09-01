package com.ntaylor.lessonscheduler.tasks.aws.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private static final String USER_NAME = "user_name";
    private static final String USER_ID = "user_id";
    private static final String ORG_ID = "org_id";
    private static final String ORG_NAME = "org_name";
    private static final String CLASS_ID = "class_id";
    private static final String CLASS_NAME = "class_name";
    private static final String DATE = "date";
    private static final String TEACHER_ID = "teacher_id";

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

    private static Classroom parseClassroom(JsonElement eTop){
        try {
            if (eTop != null){
                JsonObject top = eTop.getAsJsonObject();

                JsonElement classId = top.get("class_id");
                JsonElement orgId = top.get("org_id");
                JsonElement name = top.get("class_name");

                if (classId != null && orgId != null && name != null){
                    Classroom room = new Classroom(orgId.getAsString(), name.getAsString());
                    room.setClassId(classId.getAsString());

                    return room;
                }
            }
        }
        catch (Exception ex) {
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

    public static List<Classroom> parseClasses(String json){
        List<Classroom> classesList = new ArrayList<>();
        JsonElement eTop = (new JsonParser()).parse(json);

        if (eTop != null){
            JsonArray classes = eTop.getAsJsonObject().getAsJsonArray("classes");
            if (classes != null){
                for (JsonElement classroom : classes){
                    classesList.add(parseClassroom(classroom));
                }
            }
        }

        return classesList;
    }

    /**
     * @param json The JSON object to analyze
     * @return True if the object contains a member called "success" equal to true
     */
    public static boolean isSuccess(String json){
        JsonElement eTop = new JsonParser().parse(json);

        if (eTop != null){
            JsonElement success = eTop.getAsJsonObject().get("success");

            return success.getAsBoolean();
        }

        return false;
    }
}
