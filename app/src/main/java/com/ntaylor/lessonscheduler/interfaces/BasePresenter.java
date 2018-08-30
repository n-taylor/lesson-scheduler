package com.ntaylor.lessonscheduler.interfaces;

import android.content.Context;

import com.ntaylor.lessonscheduler.util.DataObserver;

public interface BasePresenter extends DataObserver {

    /**
     * Unhooks self from the list of data observers and ends the activity.
     */
    void end();

    /**
     * Opens the account info activity
     */
    void onAccountItemPressed(Context context);

    /**
     * Starts the classes activity
     */
    public void onClassesItemPressed(Context context);

    /**
     * Starts the upcoming activity
     */
    public void onUpcomingItemPressed(Context context);

    /**
     * Starts the users activity.
     */
    public void onUsersItemPressed(Context context);

    /**
     * Starts the assignments activity
     */
    public void onAssignmentsItemPressed(Context context);
}
