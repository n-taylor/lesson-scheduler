package com.ntaylor.lessonscheduler.util;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;

import java.util.List;

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
    void createUser(Context context, String userName, String orgName);

    /**
     * Inserts a new classroom into the classes table with the specified organization. Generates a unique ID for the class.
     * @param className The name of the class to be created.
     * @param orgId The ID of the organization under which the class should be created.
     */
    void createClassroom(@NonNull String className, @NonNull String orgId);

    /**
     * Adds the specified DataObserver to the list of observers to be notified when data has been updated.
     *
     * @param observer The observer to notify.
     */
    void addObserver(DataObserver observer);

    /**
     * Retrieves an updated list of all assignments for the current user's organization. Calls updateAssignments when
     * the list is retrieved.
     */
    void fetchAssignments();

    /**
     * Notifies all registered DataObservers that the list of assignments has been updated and provides them
     * with the updated list.
     * @param assignments The new list of assignments.
     */
    void updateAssignments(List<Assignment> assignments);

    /**
     * Requests the data provider to update its list of classes from the database
     */
    void fetchClasses();

    /**
     * Provides an updated list of classes to all registered DataObservers.
     * @param classes The updated class list for the organization
     */
    void updateClasses(List<Classroom> classes);

    /**
     * Assigns a user to teach a class on a given date.
     * @param teacherId The ID of the user to teach the class
     * @param classId The ID of the class to be taught
     * @param date The date of the class to be taught
     */
    void createAssignment(String teacherId, String classId, SimpleDate date);

}
