package com.ntaylor.lessonscheduler.util;

import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.classroom.CreateClassroomTask;

/**
 * Stores information about the current user that is logged in.
 */
public class UserInfo {

    private User user;
    private String orgName;

    private static UserInfo userInfo;

    /**
     * This method MUST be called before getUserInfo(), or it will return null.
     * Initializes the instance of UserInfo for the session.
     *
     * @param orgName The name of the user's organization
     */
    public static void startUserInfoSession(User user, String orgName){
        userInfo = new UserInfo(user, orgName);
    }

    public static void changeUserInfo(User user, String orgName){
        userInfo = new UserInfo(user, orgName);
    }

    /**
     * startUserInfoSession(...) must be called at 'login' before calling this method. If it has
     * not been called, then this method will return null. Otherwise returns the singleton instance
     * of UserInfo.
     */
    public static UserInfo getUserInfo(){
        return userInfo;
    }

    private UserInfo(User user, String orgName) {
        this.user = user;
        this.orgName = orgName;
    }

    public String getUserId(){ return this.user.getUserId(); }

    public String getOrgId() { return this.user.getOrgId(); }

    public String getUserName() { return this.user.getUserName(); }

    public String getOrgName() { return this.orgName; }

    public String getLastLesson() { return this.user.getLastClass(); }

    public String getNextLesson() { return this.user.getNextClass(); }

}
