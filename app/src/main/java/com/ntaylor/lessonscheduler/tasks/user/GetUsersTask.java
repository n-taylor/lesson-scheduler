package com.ntaylor.lessonscheduler.tasks.user;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.User;

import java.util.List;

public class GetUsersTask extends AsyncTask<Void, Void, List<User>> {

    private String orgId;
    private UsersDao dao;

    public GetUsersTask(String orgId, UsersDao dao){
        this.orgId = orgId;
        this.dao = dao;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        return dao.getUsersByOrg(orgId);
    }

    @Override
    protected void onPostExecute(List<User> users){

    }
}
