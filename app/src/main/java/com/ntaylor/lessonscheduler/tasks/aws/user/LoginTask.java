package com.ntaylor.lessonscheduler.tasks.aws.user;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.aws.util.Parser;
import com.ntaylor.lessonscheduler.tasks.aws.util.RestClient;
import com.ntaylor.lessonscheduler.util.Constants;
import com.ntaylor.lessonscheduler.util.UserInfo;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private static final String LOGIN_PATH = "/user/login/";
    private static final String GET_ORG_PATH = "/org/";

    private String username;
    private String orgName;

    private Listener listener;

    public LoginTask(String username, String orgName){
        this.username = username;
        this.orgName = orgName;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String getOrgUri = Constants.SERVER_HOST + GET_ORG_PATH + orgName.replace(" ", "%20");

        String orgResponse = RestClient.getGetResponse(getOrgUri);

        Organization parsedOrg = Parser.parseOrg(orgResponse);

        if (parsedOrg != null) {

            // The organization exists, so get the user with the given name
            String loginUri = Constants.SERVER_HOST + LOGIN_PATH + username.replace(" ", "%20");

            String response = RestClient.getGetResponse(loginUri);

            User parsedUser = Parser.parseSingleUser(response);
            if (parsedUser != null) {
                UserInfo.startUserInfoSession(parsedUser, parsedOrg.getOrgName());
            }
            return parsedUser != null;
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean loggedIn){
        if (listener != null){
            listener.onResponse(loggedIn);
        }
    }

    // private methods ==================================================================

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public interface Listener {
        /**
         * Event fires when the request has been made.
         * @param loggedIn True if the login was successful. False otherwise.
         */
        void onResponse(boolean loggedIn);
    }
}
