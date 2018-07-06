package com.ntaylor.lessonscheduler.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;

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
     * If the given username is unchanged or empty, shows an error message. Otherwise, updates the user
     *
     * @param context
     * @param name The new username
     */
    void attemptChangeUserName(Context context, String name);

    /**
     * To be called when a change of username has been attempted.
     *
     * @param changed true if the username was successfully changed. False otherwise.
     * @param name The requested new username
     */
    void onUserNameChanged(boolean changed, String name);

    /**
     * Inserts a new classroom into the classes table with the specified organization. Generates a unique ID for the class.
     * @param className The name of the class to be created.
     * @param orgId The ID of the organization under which the class should be created.
     */
    void createClassroom(@NonNull String className, @NonNull String orgId);

    /**
     * Executes a task to update the given classroom. Fetches the updated classroom list afterwards.
     * @param classroom The classroom to update.
     */
    void updateClassroom(@NonNull Classroom classroom);

    /**
     * Executes an asynchronous task the delete the given classroom. Fetches the update class list.
     * @param classroom The classroom to delete.
     */
    void deleteClassroom(@NonNull Classroom classroom);

    /**
     * Returns the list of classes. May be null.
     */
    List<Classroom> getClasses();

    /**
     * Adds the specified DataObserver to the list of observers to be notified when data has been updated.
     *
     * @param observer The observer to notify.
     */
    void addObserver(DataObserver observer);

    /**
     * Removes the specified DataObserver from the list of observers to be notified iff the
     * specified DataObserver is already a member of the list.
     *
     * @param observer The observer to remove.
     */
    void removeObserver(DataObserver observer);

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

    /**
     * Deletes all of the assignments by the given class ID
     *
     * @param classId The unique ID of the class to delete
     */
    void deleteAssignmentByClass(String classId);

}
