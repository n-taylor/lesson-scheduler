package com.ntaylor.lessonscheduler.interfaces;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ntaylor.lessonscheduler.room.entities.User;

public interface AssignContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        /**
         * Sets up the view that displays which classroom is being assigned
         * @param activity
         */
        public void initializeClassroomView(Activity activity);

        /**
         * Initializes the recycler view and sets its manager.
         */
        public void initializeRecyclerView(Context context, RecyclerView recyclerView);

        /**
         * Confirms that the user wants to assign the user to teach the class.
         *
         * @param teacher The selected user to assign to teach.
         */
        public void onTeacherPressed(final User teacher);
    }
}
