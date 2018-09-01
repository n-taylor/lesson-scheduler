package com.ntaylor.lessonscheduler.tasks.aws.user;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public class ChangeUserNameTask extends AsyncTask<Void, Void, Boolean> {

    private String userId;
    private String username;
    private UsersDao dao;

    public ChangeUserNameTask(String userId, String username){
        this.userId = userId;
        this.username = username;
        this.dao = new UsersDao();
    }

    /**
     * @return true if the username was updated (if the new name is different and valid)
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        if (username.trim().isEmpty()){
            return false;
        }
        User takenName = dao.getUserByName(username);
        if (takenName == null){
            // The user name is not taken already

            User toUpdate = new User(username, UserInfo.getUserInfo().getOrgId());
            toUpdate.setUserId(UserInfo.getUserInfo().getUserId());


            if (UserInfo.getUserInfo().getUserId().equals(userId)) {
                // Update the user in the database
                dao.update(toUpdate);

                UserInfo.changeUserInfo(toUpdate, UserInfo.getUserInfo().getOrgName());

                return true;
            }
            return false;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean changed){
        DataProviderFactory.getDataProviderInstance().onUserNameChanged(changed, username);
    }
}
