package com.ntaylor.lessonscheduler.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.activities.UpcomingActivity;
import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.dao.OrganizationsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.database.LessonsRoomDatabase;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.tasks.CreateUserTask;
import com.ntaylor.lessonscheduler.tasks.RoomTask;

import java.util.List;

/**
 * Provides access to data from the Room SQLite wrapper.
 */
public class RoomRepository implements DataProvider {

    private UsersDao usersDao;
    private ClassesDao classesDao;
    private OrganizationsDao organizationsDao;
    private AssignmentsDao assignmentsDao;

    private Activity activity;

    private View signUpView;

    public RoomRepository(Activity activity){
        LessonsRoomDatabase db = LessonsRoomDatabase.getInstance(activity);
        usersDao = db.getUsersDao();
        classesDao = db.getClassesDao();
        organizationsDao = db.getOrganizationsDao();
        assignmentsDao = db.getAssignmentsDao();

        signUpView = activity.findViewById(R.id.sign_up_activity_id);
        this.activity = activity;
    }


    /**
     * If userName already exists, displays an error message. Otherwise, creates the user and adds
     * it to the specified organization.
     *
     * @param userName The requested user name
     * @param orgName  The name of the organization.
     */
    @Override
    public void createUser(final String userName, final String orgName) {
        new CreateUserTask(activity, usersDao, organizationsDao, userName, orgName).execute();
    }
}
