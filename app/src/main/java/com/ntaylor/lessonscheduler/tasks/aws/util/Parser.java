package com.ntaylor.lessonscheduler.tasks.aws.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;

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
                if (userName != null){
                    String org = (top.get("org_id") != null) ? top.get("org_id").getAsString() : "";
                    User user = new User(userName.getAsString(), org);

                    JsonElement userId = top.get("user_id");
                    if (userId != null){

                        user.setUserId(userId.getAsString());
                        return user;
                    }
                }
            }
        }
        catch (Exception ex){
            return null;
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
}
