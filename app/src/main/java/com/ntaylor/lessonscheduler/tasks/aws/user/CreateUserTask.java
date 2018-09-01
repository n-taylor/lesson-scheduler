package com.ntaylor.lessonscheduler.tasks.aws.user;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.aws.organization.OrgDao;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

public class CreateUserTask extends AsyncTask<Void, Void, Boolean> {

    private String username;
    private String orgId;
    private OrgDao orgDao;
    private UsersDao usersDao;

    public CreateUserTask(String username, String orgId){
        this.username = username;
        this.orgId = orgId;
        this.orgDao = new OrgDao();
        this.usersDao = new UsersDao();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        // check that the organization exists
        Organization org = null;
        Organization orgs = orgDao.getOrgById(orgId);
        if (orgs == null){
            return false;
        }

        // now we know the organization exists, make sure the username isn't taken
        User users = usersDao.getUserByName(username);
        if (users != null){
            return false;
        }

        if (username == null || username.isEmpty()){
            return false;
        }

        // the org exists and the username is available. Lets create the user.
        User user = new User(username, orgId);
        usersDao.insert(user);
        return true;
    }

    @Override
    public void onPostExecute(Boolean success){
        DataProviderFactory.getDataProviderInstance().onUserCreationAttempted(success, username);
    }
}
