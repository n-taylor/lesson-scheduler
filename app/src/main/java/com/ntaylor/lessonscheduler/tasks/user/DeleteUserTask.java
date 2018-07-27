package com.ntaylor.lessonscheduler.tasks.user;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class DeleteUserTask extends AsyncTask<Void, Void, Boolean> {

    private User user;
    private UsersDao dao;

    public DeleteUserTask(User user, UsersDao dao){
        this.user = user;
        this.dao = dao;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        int deleted = dao.deleteById(user.getUserId());
        return deleted > 0;
    }

    @Override
    protected void onPostExecute(Boolean success){
        DataProviderFactory.getDataProviderInstance().onUserDeleted(success);
        DataProviderFactory.getDataProviderInstance().fetchUsers();
    }
}
