package com.ntaylor.lessonscheduler.tasks.aws.user;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.aws.assignment.DeleteAssignmentTask;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class DeleteUserTask extends AsyncTask<Void, Void, Boolean> {

    private UsersDao dao;
    private String userId;

    public DeleteUserTask(User user){
        this.userId = user.getUserId();
        this.dao = new UsersDao();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return dao.delete(userId);
    }

    @Override
    protected void onPostExecute(Boolean success){
        DataProviderFactory.getDataProviderInstance().onUserDeleted(success);
        DataProviderFactory.getDataProviderInstance().fetchUsers();
    }
}
