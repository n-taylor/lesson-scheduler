package com.ntaylor.lessonscheduler.tasks.user;

import android.content.Context;

import com.ntaylor.lessonscheduler.room.dao.OrganizationsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.util.RoomTask;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;
import java.util.Locale;

public class CreateUserTask extends RoomTask<Void, Void, Boolean> {

    private UsersDao usersDao;
    private OrganizationsDao organizationsDao;
    private String userName;
    private String orgId;

    public CreateUserTask(String userName, String orgId, UsersDao usersDao, OrganizationsDao organizationsDao){
        this.usersDao = usersDao;
        this.organizationsDao = organizationsDao;
        this.userName = userName;
        this.orgId = orgId;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // check that the organization exists
        Organization org = null;
        List<Organization> orgs = organizationsDao.getOrganizationById(orgId);
        if (orgs == null || orgs.size() == 0){
            return false;
        }

        // now we know the organization exists, make sure the username isn't taken
        List<User> users = usersDao.getUserByUserName(userName);
        if (users == null || users.size() > 0){
            return false;
        }

        if (userName == null || userName.isEmpty()){
            return false;
        }

        // the org exists and the username is available. Lets create the user.
        User user = new User(userName, orgId);
        usersDao.insert(user);
        return true;
    }

    @Override
    public void onPostExecute(Boolean success){
        DataProviderFactory.getDataProviderInstance().onUserCreationAttempted(success, userName);
    }
}
