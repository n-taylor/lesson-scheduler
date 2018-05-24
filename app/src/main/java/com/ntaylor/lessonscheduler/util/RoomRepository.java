package com.ntaylor.lessonscheduler.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.view.View;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.activities.UpcomingActivity;
import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.dao.OrganizationsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.database.LessonsRoomDatabase;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.tasks.CreateUserTask;
import com.ntaylor.lessonscheduler.tasks.assignment.CreateAssignmentTask;
import com.ntaylor.lessonscheduler.tasks.assignment.GetAssignmentsTask;
import com.ntaylor.lessonscheduler.tasks.classroom.CreateClassroomTask;
import com.ntaylor.lessonscheduler.tasks.classroom.GetClassroomsTask;

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
        LessonsRoomDatabase db = LessonsRoomDatabase.getInstance(activity);
        usersDao = db.getUsersDao();
        classesDao = db.getClassesDao();
        organizationsDao = db.getOrganizationsDao();
        assignmentsDao = db.getAssignmentsDao();

        signUpView = activity.findViewById(R.id.sign_up_activity_id);
    }

    /**
     * If userName already exists, displays an error message. Otherwise, creates the user and adds
     * it to the specified organization.
     *
     * @param userName The requested user name
     * @param orgName  The name of the organization.
     */
    @Override
    public void createUser(Context context, final String userName, final String orgName) {
        new CreateUserTask(context, usersDao, organizationsDao, userName, orgName).execute();
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
     * Adds the specified DataObserver to the list of observers to be notified when data has been updated.
     *
     * @param observer The observer to notify.
     */
    @Override
    public void addObserver(DataObserver observer){
        this.observers.add(observer);
    }

    /**
     * Retrieves an updated list of all assignments for the current user's organization. Calls updateAssignments when
     * the list is retrieved.
     */
    @Override
    public void fetchAssignments() {
        fetchClasses();
//        GetAssignmentsTask task = new GetAssignmentsTask(
//                UserInfo.getUserInfo().getOrgId(),
//                classes.get(0).getClassId(),
//                -1,
//                GetAssignmentsTask.FUTURE,
//                assignmentsDao);
//        task.execute();
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

        // For testing purposes, get assignments
        GetAssignmentsTask task = new GetAssignmentsTask(
                UserInfo.getUserInfo().getOrgId(),
                classes.get(0).getClassId(),
                -1,
                GetAssignmentsTask.FUTURE,
                assignmentsDao);
        task.execute();
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

}
