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
    private static final String POST_USER = "/user";
    private static final String DELETE_USER = "/user/";

    public List<User> getUsersByOrg(String orgId){

        String usersUri = Constants.SERVER_HOST + GET_USERS_PATH + orgId;

        String response = RestClient.getGetResponse(usersUri);

        return Parser.parseUsers(response);
    }

    public User getUserByName(String name){
        String uri = Constants.SERVER_HOST + GET_USER_BY_NAME + name.replace(" ", "%20");

        return Parser.parseSingleUser(RestClient.getGetResponse(uri));
    }

    public void update(User user){
        String uri = Constants.SERVER_HOST + UPDATE_USER;

        RestClient.getPostResponse(uri, stringify(user));
    }

    public void insert(User user){
        String uri = Constants.SERVER_HOST + POST_USER;

        String response = RestClient.getPostResponse(uri, stringify(user));
    }

    public boolean delete(String userId){
        String uri = Constants.SERVER_HOST + DELETE_USER + userId;

        String result = RestClient.getPutResponse(uri, "{}");

        return Parser.isSuccess(result);
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

    private String stringify(String userId){
        JsonObject top = new JsonObject();
        top.addProperty("user_id", userId);

        return top.toString();
    }

}
