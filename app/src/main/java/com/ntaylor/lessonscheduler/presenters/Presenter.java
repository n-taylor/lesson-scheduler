package com.ntaylor.lessonscheduler.presenters;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;

import com.ntaylor.lessonscheduler.activities.AccountActivity;
import com.ntaylor.lessonscheduler.activities.ClassesActivity;
import com.ntaylor.lessonscheduler.activities.UpcomingActivity;
import com.ntaylor.lessonscheduler.activities.UsersActivity;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public abstract class Presenter implements DataObserver {

    /**
     * Opens the account info activity
     * @param context
     */
    public void onAccountItemPressed(Context context){
        Intent intent = new Intent(context, AccountActivity.class);
        intent.putExtra(AccountActivity.EXTRA_USER_NAME, UserInfo.getUserInfo().getUserName());
        intent.putExtra(AccountActivity.EXTRA_USER_ID, UserInfo.getUserInfo().getUserId());
        intent.putExtra(AccountActivity.EXTRA_LAST_LESSON, UserInfo.getUserInfo().getLastLesson());
        intent.putExtra(AccountActivity.EXTRA_NEXT_LESSON, UserInfo.getUserInfo().getNextLesson());
        context.startActivity(intent);
    }

    /**
     * Starts the classes activity
     */
    public void onClassesItemPressed(Context context){
        Intent intent = new Intent(context, ClassesActivity.class);
        context.startActivity(intent);
    }

    /**
     * Starts the upcoming activity
     */
    public void onUpcomingItemPressed(Context context){
        Intent intent = new Intent(context, UpcomingActivity.class);
        context.startActivity(intent);
    }

    /**
     * Starts the users activity.
     */
    public void onUsersItemPressed(Context context){
        Intent intent = new Intent(context, UsersActivity.class);
        context.startActivity(intent);
    }

    // Implement DataObserver methods =======================================================

    /**
     * This will be called when the list of assignments has been modified.
     *
     * @param assignments The class-teacher assignments.
     */
    @Override
    public void onAssignmentsUpdated(List<Assignment> assignments) {

    }

    /**
     * this will be called when the list of classes for the organization has been modified.
     *
     * @param classes The list of classes.
     */
    @Override
    public void onClassesUpdated(List<Classroom> classes) {

    }

    /**
     * To be called when an attempt to modify the user name has been fully attempted.
     *
     * @param successful true if the username was changed, false if not valid or already taken.
     * @param name       The new username requested
     */
    @Override
    public void onUserNameChanged(boolean successful, String name) {

    }

    /**
     * To be called when an attempt to create a user has either succeeded or failed.
     * @param success True if the user was created.
     */
    @Override
    public void onUserCreationAttempted(boolean success, String name){

    }

    /**
     * To be called when the list of users in an organization has been updated.
     * Initialize the recyclerView adapter
     * @param users The current list of teachers in the user's organization.
     */
    @Override
    public void onUsersUpdated(List<User> users) {

    }

    @Override
    public void onUserDeleted(boolean success){

    }
}
