package com.ntaylor.lessonscheduler.tasks.aws.user;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.aws.util.Parser;
import com.ntaylor.lessonscheduler.tasks.aws.util.RestClient;
import com.ntaylor.lessonscheduler.util.Constants;

import org.json.JSONStringer;

import java.util.List;

public class UsersDao {

    private static final String GET_USERS_PATH = "/user/byOrg/";
    private static final String UPDATE_USER = "/user/update";
    private static final String GET_USER_BY_NAME = "/user/byName/";

    public List<User> getUsersByOrg(String orgId){

        String usersUri = Constants.SERVER_HOST + GET_USERS_PATH + orgId;

        return Parser.parseUsers(RestClient.getGetResponse(usersUri));
    }

    public User getUserByName(String name){
        String uri = Constants.SERVER_HOST + GET_USER_BY_NAME + name.replace(" ", "%20");

        return Parser.parseSingleUser(RestClient.getGetResponse(uri));
    }

    public void update(User user){
        String uri = Constants.SERVER_HOST + UPDATE_USER;

        RestClient.getPostResponse(uri, stringify(user));
    }

    private String stringify(User user){
        JsonObject top = new JsonObject();
        top.addProperty("user_name", user.getUserName());
        top.addProperty("user_id", user.getUserId());
        top.addProperty("org_id", user.getOrgId());
        top.addProperty("last_class", user.getLastClass());
        top.addProperty("next_class", user.getNextClass());
        top.addProperty("auth_level", user.getAuthLevel());

        return top.toString();
    }

}
