package com.ntaylor.lessonscheduler.util;

import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.classroom.CreateClassroomTask;

/**
 * Stores information about the current user that is logged in.
 */
public class UserInfo {

    private String userId;
    private String userName;
    private String orgId;
    private String orgName;

    private static UserInfo userInfo;

    /**
     * This method MUST be called before getUserInfo(), or it will return null.
     * Initializes the instance of UserInfo for the session.
     *
     * @param userId The user ID of the user logged in
     * @param userName The user name of the user
     * @param orgId The ID of the organization the user belongs to
     * @param orgName The name of the user's organization
     */
    public static void startUserInfoSession(String userId, String userName, String orgId, String orgName){
        userInfo = new UserInfo(userId, userName, orgId, orgName);
    }

    public static void changeUserInfo(User user, String orgName){
        userInfo = new UserInfo(user.getUserId(), user.getUserName(), user.getOrgId(), orgName);
    }

    /**
     * startUserInfoSession(...) must be called at 'login' before calling this method. If it has
     * not been called, then this method will return null. Otherwise returns the singleton instance
     * of UserInfo.
     */
    public static UserInfo getUserInfo(){
        return userInfo;
    }

    private UserInfo(String userId, String userName, String orgId, String orgName) {
        this.userId = userId;
        this.userName = userName;
        this.orgId = orgId;
        this.orgName = orgName;
    }

    public String getUserId(){ return this.userId; }

    public String getOrgId() { return this.orgId; }

    public String getUserName() { return this.userName; }

    public void setUserName(String name) { this.userName = name; }

    public String getOrgName() { return this.orgName; }

}
