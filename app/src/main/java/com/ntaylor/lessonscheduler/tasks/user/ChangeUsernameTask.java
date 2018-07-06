package com.ntaylor.lessonscheduler.tasks.user;

import android.content.Context;

import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.util.RoomTask;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public class ChangeUsernameTask extends RoomTask<Void, Void, Boolean> {

    private Context context;
    private User user;
    private String name;
    private UsersDao dao;

    public ChangeUsernameTask(Context context, String name, UsersDao dao){
        this.context = context;
        this.name = name;
        this.user = new User(name, UserInfo.getUserInfo().getOrgId());
        this.user.setUserId(UserInfo.getUserInfo().getUserId());
        this.dao = dao;
    }

    /**
     * @return true if the username was updated (if the new name is different and valid)
     */
    @Override
    protected Boolean doInBackground(Void... voids) {
        if (name.trim().isEmpty()){
            return false;
        }
        List<User> takenName = dao.getUserByUserName(name);
        if (takenName != null && takenName.size() < 1){
            // The user name is not taken already
            dao.deleteByUserName(UserInfo.getUserInfo().getUserName());
            User nextUser = new User(name, UserInfo.getUserInfo().getOrgId());
            dao.insert(nextUser);
            UserInfo.changeUserInfo(nextUser, UserInfo.getUserInfo().getOrgName());
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean changed){
        DataProviderFactory.getDataProviderInstance().onUserNameChanged(changed, name);
    }
}
