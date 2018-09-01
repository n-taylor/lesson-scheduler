package com.ntaylor.lessonscheduler.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.activities.UpcomingActivity;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.aws.classroom.GetClassesTask;
import com.ntaylor.lessonscheduler.tasks.aws.user.GetUsersTask;
import com.ntaylor.lessonscheduler.tasks.aws.user.LoginTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A data repository using AWS EC2 as its backend.
 */
public class AwsRepository implements DataProvider {

    private static final String LOGIN_FAIL = "Error logging you in. PLease check your credentials.";
    private static final String LOGIN_SUCCESS = "Successfully logged in as %s";

    private List<DataObserver> observers;
    private List<Classroom> classrooms;

    public AwsRepository(){
        observers = new ArrayList<>();
    }

    /**
     * If userName already exists, displays an error message. Otherwise, creates the user and adds
     * it to the specified organization.
     *
     * @param context
     * @param userName The requested user name
     * @param orgName  The name of the organization.
     */
    @Override
    public void login(final Context context, final String userName, String orgName) {
        LoginTask task = new LoginTask(userName, orgName);
        task.setListener(new LoginTask.Listener() {
            @Override
            public void onResponse(boolean loggedIn) {
                if (loggedIn){
                    String message = String.format(Locale.US, LOGIN_SUCCESS, userName);
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                    // Send the user to the Upcoming activity
                    context.startActivity(new Intent(context, UpcomingActivity.class));
                }
                else {
                    Toast.makeText(context, LOGIN_FAIL, Toast.LENGTH_SHORT).show();
                }
            }
        });
        task.execute();
    }

    /**
     * Attempts to create a user in the specified organization. Alerts DataObservers through
     * DataProvider.onUserCreationAttempted().
     *
     * @param userName The username requested.
     * @param orgId    The ID of the organization requested.
     */
    @Override
    public void attemptCreateUser(String userName, String orgId) {

    }

    /**
     * Alerts DataObservers whether a user was created successfully.
     *
     * @param success true if the user was created.
     * @param name
     */
    @Override
    public void onUserCreationAttempted(boolean success, String name) {

    }

    /**
     * If the given username is unchanged or empty, shows an error message. Otherwise, updates the user
     *
     * @param context
     * @param userId  The id of the user whose name should be changed
     * @param name    The new username
     */
    @Override
    public void attemptChangeUserName(Context context, String userId, String name) {

    }

    /**
     * To be called when a change of username has been attempted.
     *
     * @param changed true if the username was successfully changed. False otherwise.
     * @param name    The requested new username
     */
    @Override
    public void onUserNameChanged(boolean changed, String name) {

    }

    /**
     * Deletes the given user.
     *
     * @param user The user to be deleted.
     */
    @Override
    public void deleteUser(User user) {

    }

    /**
     * Notifies data observers whether a user has been deleted.
     *
     * @param success True if the user was successfully deleted
     */
    @Override
    public void onUserDeleted(boolean success) {

    }

    /**
     * Retrieves the users associated with the same organization as the current user
     */
    @Override
    public void fetchUsers() {
        GetUsersTask task = new GetUsersTask(UserInfo.getUserInfo().getOrgId());
        task.execute();
    }

    /**
     * Notifies all data observers that the list of users for the organization has been updated.
     *
     * @param users The updated list of users in the organization
     */
    @Override
    public void updateUsers(List<User> users) {
        for (DataObserver observer : observers){
            observer.onUsersUpdated(users);
        }
    }

    /**
     * Inserts a new classroom into the classes table with the specified organization. Generates a unique ID for the class.
     *
     * @param className The name of the class to be created.
     * @param orgId     The ID of the organization under which the class should be created.
     */
    @Override
    public void createClassroom(@NonNull String className, @NonNull String orgId) {

    }

    /**
     * Executes a task to update the given classroom. Fetches the updated classroom list afterwards.
     *
     * @param classroom The classroom to update.
     */
    @Override
    public void updateClassroom(@NonNull Classroom classroom) {

    }

    /**
     * Executes an asynchronous task the delete the given classroom. Fetches the update class list.
     *
     * @param classroom The classroom to delete.
     */
    @Override
    public void deleteClassroom(@NonNull Classroom classroom) {

    }

    /**
     * Notifies observers that a classroom has been deleted.
     */
    @Override
    public void onClassroomDeleted() {

    }

    /**
     * Returns the list of classes. May be null.
     */
    @Override
    public List<Classroom> getClasses() {
        return this.classrooms;
    }

    /**
     * Adds the specified DataObserver to the list of observers to be notified when data has been updated.
     *
     * @param observer The observer to notify.
     */
    @Override
    public void addObserver(DataObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes the specified DataObserver from the list of observers to be notified iff the
     * specified DataObserver is already a member of the list.
     *
     * @param observer The observer to remove.
     */
    @Override
    public void removeObserver(DataObserver observer) {
        observers.remove(observer);
    }

    /**
     * Retrieves an updated list of all assignments for the current user's organization. Calls updateAssignments when
     * the list is retrieved.
     */
    @Override
    public void fetchAssignments() {

    }

    /**
     * Notifies all registered DataObservers that the list of assignments has been updated and provides them
     * with the updated list.
     *
     * @param assignments The new list of assignments.
     */
    @Override
    public void updateAssignments(List<Assignment> assignments) {

    }

    /**
     * Deletes the specified assignment from the database.
     *
     * @param assignment The assignment to delete.
     */
    @Override
    public void deleteAssignment(Assignment assignment) {

    }

    /**
     * Notifies observers whether an assignment was successufully deleted.
     *
     * @param success True if the assignment was fully deleted.
     */
    @Override
    public void onAssignmentDeleted(boolean success) {

    }

    /**
     * Requests the data provider to update its list of classes from the database
     */
    @Override
    public void fetchClasses() {
        GetClassesTask task = new GetClassesTask(UserInfo.getUserInfo().getOrgId(), false);
        task.execute();
    }

    /**
     * Provides an updated list of classes to all registered DataObservers.
     *
     * @param classes The updated class list for the organization
     */
    @Override
    public void updateClasses(List<Classroom> classes) {
        this.classrooms = classes;

        for (DataObserver observer : observers){
            observer.onClassesUpdated(classes);
        }
    }

    /**
     * Assigns a user to teach a class on a given date.
     *
     * @param teacherId The ID of the user to teach the class
     * @param classId   The ID of the class to be taught
     * @param date      The date of the class to be taught
     */
    @Override
    public void createAssignment(String teacherId, String classId, SimpleDate date) {

    }

    /**
     * Deletes all of the assignments by the given class ID
     *
     * @param classId The unique ID of the class to delete
     */
    @Override
    public void deleteAssignmentByClass(String classId) {

    }
}
