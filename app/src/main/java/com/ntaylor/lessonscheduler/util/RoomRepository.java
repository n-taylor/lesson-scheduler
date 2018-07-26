package com.ntaylor.lessonscheduler.util;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.dao.OrganizationsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.database.LessonsRoomDatabase;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.user.ChangeUsernameTask;
import com.ntaylor.lessonscheduler.tasks.user.CreateUserTask;
import com.ntaylor.lessonscheduler.tasks.assignment.CreateAssignmentTask;
import com.ntaylor.lessonscheduler.tasks.assignment.DeleteAssignmentsTask;
import com.ntaylor.lessonscheduler.tasks.assignment.GetAssignmentsTask;
import com.ntaylor.lessonscheduler.tasks.classroom.CreateClassroomTask;
import com.ntaylor.lessonscheduler.tasks.classroom.DeleteClassroomTask;
import com.ntaylor.lessonscheduler.tasks.classroom.GetClassroomsTask;
import com.ntaylor.lessonscheduler.tasks.classroom.UpdateClassroomTask;
import com.ntaylor.lessonscheduler.tasks.user.GetUsersTask;
import com.ntaylor.lessonscheduler.tasks.user.LoginTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides access to data from the Room SQLite wrapper.
 */
public class RoomRepository implements DataProvider {

    private UsersDao usersDao;
    private ClassesDao classesDao;
    private OrganizationsDao organizationsDao;
    private AssignmentsDao assignmentsDao;

    private ArrayList<DataObserver> observers;

    private View signUpView;

    private List<Assignment> assignments;
    private List<Classroom> classes;

    RoomRepository(Activity activity){

        observers = new ArrayList<>();

        LessonsRoomDatabase db = LessonsRoomDatabase.getInstance(activity);
        usersDao = db.getUsersDao();
        classesDao = db.getClassesDao();
        organizationsDao = db.getOrganizationsDao();
        assignmentsDao = db.getAssignmentsDao();

        signUpView = activity.findViewById(R.id.sign_up_activity_id);
    }

    /**
     * Returns the list of classes. May be null.
     */
    public List<Classroom> getClasses(){
        return classes;
    }

    /**
     * If userName already exists, displays an error message. Otherwise, creates the user and adds
     * it to the specified organization.
     *
     * @param userName The requested user name
     * @param orgName  The name of the organization.
     */
    @Override
    public void login(Context context, final String userName, final String orgName) {
        new LoginTask(context, userName, orgName, usersDao, organizationsDao).execute();
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
        CreateUserTask task = new CreateUserTask(userName, orgId, usersDao, organizationsDao);
        task.execute();
    }

    /**
     * Alerts DataObservers whether a user was created successfully.
     *
     * @param success true if the user was created.
     * @param name The name of the user created.
     */
    @Override
    public void onUserCreationAttempted(boolean success, String name) {
        for (DataObserver observer : observers){
            observer.onUserCreationAttempted(success, name);
        }
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
        ChangeUsernameTask task = new ChangeUsernameTask(context, userId, name, usersDao);
        task.execute();
    }

    /**
     * To be called when a change of username has been attempted.
     *
     * @param changed true if the username was successfully changed. False otherwise.
     * @param name
     */
    @Override
    public void onUserNameChanged(boolean changed, String name) {
        for(DataObserver observer : observers){
            observer.onUserNameChanged(changed, name);
        }
    }

    /**
     * Retrieves the users associated with the same organization as the current user.
     */
    @Override
    public void fetchUsers() {
        GetUsersTask task = new GetUsersTask(UserInfo.getUserInfo().getOrgId(), usersDao, assignmentsDao);
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
        CreateClassroomTask task = new CreateClassroomTask(className, orgId, classesDao);
        task.execute();
    }

    /**
     * Executes a task to update the given classroom. Fetches the updated classroom list afterwards.
     *
     * @param classroom The classroom to update.
     */
    @Override
    public void updateClassroom(@NonNull Classroom classroom) {
        UpdateClassroomTask task = new UpdateClassroomTask(classroom, classesDao);
        task.execute();
    }

    /**
     * Executes an asynchronous task the delete the given classroom. Fetches the update class list.
     *
     * @param classroom The classroom to delete.
     */
    @Override
    public void deleteClassroom(@NonNull Classroom classroom) {
        DeleteClassroomTask task = new DeleteClassroomTask(classroom, classesDao);
        task.execute();
    }

    /**
     * Adds the specified DataObserver to the list of observers to be notified when data has been updated.
     *
     * @param observer The observer to notify.
     */
    @Override
    public void addObserver(DataObserver observer){
        this.observers.add(observer);
    }

    /**
     * Removes the specified DataObserver from the list of observers to be notified iff the
     * specified DataObserver is already a member of the list.
     *
     * @param observer The observer to remove.
     */
    @Override
    public void removeObserver(DataObserver observer) {
        if (observers.contains(observer)){
            observers.remove(observer);
        }
    }

    /**
     * Retrieves an updated list of all assignments for the current user's organization. Calls updateAssignments when
     * the list is retrieved.
     */
    @Override
    public void fetchAssignments() {
        // For testing purposes, delete all assignments
//        deleteAssignmentByClass("1c273b19-1610-457f-a5b7-702e7717c6b3");

        // For testing purposes, add an assignment
//        createAssignment(UserInfo.getUserInfo().getUserId(), "1c273b19-1610-457f-a5b7-702e7717c6b3", new SimpleDate());

        GetAssignmentsTask task = new GetAssignmentsTask(
                UserInfo.getUserInfo().getOrgId(),
                null,
                GetAssignmentsTask.BY_ORG_ID,
                GetAssignmentsTask.FUTURE,
                assignmentsDao);
        task.execute();
    }

    /**
     * Notifies all registered DataObservers that the list of assignments has been updated and provides them
     * with the updated list.
     *
     * @param assignments The new list of assignments.
     */
    public void updateAssignments(List<Assignment> assignments){
        this.assignments = assignments;
        for (DataObserver observer : observers){
            observer.onAssignmentsUpdated(assignments);
        }
    }

    /**
     * Requests the data provider to update its list of classes from the database
     */
    @Override
    public void fetchClasses() {
        GetClassroomsTask task = new GetClassroomsTask(UserInfo.getUserInfo().getOrgId(), false, classesDao);
        task.execute();
    }

    /**
     * Provides an updated list of classes to all registered DataObservers.
     *
     * @param classes The updated class list for the organization
     */
    @Override
    public void updateClasses(List<Classroom> classes) {
        this.classes = classes;
        // For testing purposes, create an assignment
//        createAssignment(UserInfo.getUserInfo().getUserId(), classes.get(0).getClassId(), new SimpleDate());

        for (DataObserver observer : observers){
            observer.onClassesUpdated(classes);
        }
    }

    /**
     * Assigns a user to teach a class on a given date.
     *
     * @param teacherId The ID of the user to teach the class
     * @param classId   The ID of the class to be taught
     * @param date      The date in serialized format
     */
    @Override
    public void createAssignment(String teacherId, String classId, SimpleDate date) {
        CreateAssignmentTask task = new CreateAssignmentTask(UserInfo.getUserInfo().getOrgId(), classId, teacherId, date, assignmentsDao);
        task.execute();
    }

    /**
     * Deletes all of the assignments by the given class ID
     *
     * @param classId The unique ID of the class to delete
     */
    @Override
    public void deleteAssignmentByClass(String classId) {
        DeleteAssignmentsTask task = new DeleteAssignmentsTask(classId, DeleteAssignmentsTask.BY_CLASS_ID, assignmentsDao);
        task.execute();
    }


}
