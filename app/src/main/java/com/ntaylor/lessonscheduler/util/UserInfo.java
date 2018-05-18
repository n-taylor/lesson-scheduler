package com.ntaylor.lessonscheduler.util;

/**
 * Stores information about the current user that is logged in.
 */
public class UserInfo {

    private String userId;
    private String userName;
    private String orgId;
    private String orgName;

    private static UserInfo userInfo;

    public static void startUserInfoSession(String userId, String userName, String orgId, String orgName){
        userInfo = new UserInfo(userId, userName, orgId, orgName);
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

}
