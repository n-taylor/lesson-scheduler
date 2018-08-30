package com.ntaylor.lessonscheduler.interfaces;

import android.content.Context;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.room.entities.Classroom;

public interface ClassesContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        /**
         * Initializes the list view and sets its manager.
         * @param listView
         */
        void initializeListView(ListView listView);

        /**
         * Displays account information for the current user.
         */
        void onAccountItemPressed(Context context);

        /**
         * To be called when the user presses a classroom in the list of classes.
         * Starts the ClassroomEditActivity with the given classroom.
         * @param classroom The classroom selected.
         */
        public void onClassroomPressed(Classroom classroom);

        /**
         * Deletes the classroom indicated.
         */
        public void onDeletePressed(final Classroom classroom);

        /**
         * To be called when the user presses a button to add a classroom.
         * Starts the ClassroomEditActivity with the intention of adding a classroom.
         */
        public void onAddButtonPressed();
    }
}
