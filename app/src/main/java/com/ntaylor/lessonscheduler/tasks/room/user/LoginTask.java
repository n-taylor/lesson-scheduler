package com.ntaylor.lessonscheduler.tasks.room.user;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.activities.UsersActivity;
import com.ntaylor.lessonscheduler.room.dao.OrganizationsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public class LoginTask extends AsyncTask<Void, Void, Boolean> {

    private static final String WRONG_ORG_MESSAGE = "The organization specified is incorrect";
    private static final String LOGIN_SUCCESSFUL = "The login was successful";

    private Context context;
    private String userName;
    private String orgName;
    private UsersDao usersDao;
    private OrganizationsDao orgDao;

    public LoginTask(Context context, String userName, String orgName, UsersDao usersDao, OrganizationsDao orgDao){
        this.context = context;
        this.userName = userName;
        this.orgName = orgName;
        this.usersDao = usersDao;
        this.orgDao = orgDao;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        Organization org = null;
        List<Organization> orgs = orgDao.getOrganizationByName(orgName);
        if (orgs != null && orgs.size() > 0){
            org = orgs.get(0);
        }

        User user = null;
        List<User> users = usersDao.getUserByUserName(userName);
        if (users != null && users.size() > 0){
            user = users.get(0);
        }

        if (user != null && org != null && user.getOrgId().equals(org.getOrgId())){
            // Start user info session
            UserInfo.startUserInfoSession(user, org.getOrgName());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success){
        if (success){
            Toast.makeText(context, LOGIN_SUCCESSFUL, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, UsersActivity.class);
            context.startActivity(intent);
        }
        else {
            Toast.makeText(context, WRONG_ORG_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }
}
