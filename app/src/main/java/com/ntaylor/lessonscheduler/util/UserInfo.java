package com.ntaylor.lessonscheduler.util;

/**
 * Stores information about the current user that is logged in.
 */
public class UserInfo {

    private static boolean isLoggedIn = false;
    private static String userId;
    private static String userName;
    private static String orgId;
    private static String orgName;

    private static boolean isLoggedIn(){ return isLoggedIn; }
}
