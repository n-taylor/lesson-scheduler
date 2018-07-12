package com.ntaylor.lessonscheduler.tasks.user;

import android.content.Context;
import android.content.Intent;

import com.ntaylor.lessonscheduler.activities.AccountActivity;
import com.ntaylor.lessonscheduler.activities.UpcomingActivity;
import com.ntaylor.lessonscheduler.room.dao.OrganizationsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.util.RoomTask;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public class CreateUserTask extends RoomTask<Void, Void, Boolean> {

    private static final String WRONG_ORG_MESSAGE = "The organization specified is incorrect";
    private static final String LOGIN_SUCCESSFUL = "The login was successful";

    private UsersDao usersDao;
    private OrganizationsDao organizationsDao;
    private String userName;
    private String orgName;
    private Context context;

    public CreateUserTask(Context context, UsersDao usersDao, OrganizationsDao organizationsDao, String userName, String orgName){
        this.context = context;
        this.usersDao = usersDao;
        this.organizationsDao = organizationsDao;
        this.userName = userName;
        this.orgName = orgName;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        List<Organization> orgs = organizationsDao.getOrganizationByName(orgName);
        if (orgs.size() < 1){
            organizationsDao.insert(new Organization(orgName));
            orgs = organizationsDao.getOrganizationByName(orgName);
        }
        Organization org = orgs.get(0);
        String orgId = org.getOrgId();
        List<User> users = usersDao.getUserByUserName(userName);
        if (users.size() < 1){
            usersDao.insert(new User(userName, orgId));
            users = usersDao.getUserByUserName(userName);
        }
        else if (!orgId.equals(users.get(0).getOrgId())){
            //showToast(WRONG_ORG_MESSAGE, context);
            return false;
        }

        User user = users.get(0);

        // Basically 'login' for testing purposes
        UserInfo.startUserInfoSession(user.getUserId(), user.getUserName(), org.getOrgId(), org.getOrgName());

        return true;
    }

    @Override
    public void onPostExecute(Boolean successful){
            if (successful) {
                showToast(LOGIN_SUCCESSFUL, context);
                Intent intent = new Intent(context, UpcomingActivity.class);
                context.startActivity(intent);
            }
            else
                showToast(WRONG_ORG_MESSAGE, context);
    }
}
