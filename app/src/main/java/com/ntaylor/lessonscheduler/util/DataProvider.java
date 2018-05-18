package com.ntaylor.lessonscheduler.util;

import com.ntaylor.lessonscheduler.room.entities.User;

/**
 * Defines how the data can be accessed. Currently implemented by RoomRepository.
 */
public interface DataProvider {

    /**
     * If userName already exists, displays an error message. Otherwise, creates the user and adds
     * it to the specified organization.
     * @param userName The requested user name
     * @param orgName The name of the organization.
     */
    void createUser(String userName, String orgName);

}
