package com.ntaylor.lessonscheduler.interfaces;

import android.content.Context;
import android.widget.ListView;

public interface UpcomingContract {
    public interface View {
        void destroySelf();
    }

    public interface Presenter extends BasePresenter {
        /**
         * Shows a dialog that allows the user to select the class to which they want to assign a teacher
         * @param date The date of the assignment to be made.
         * @param unassigned The names of all the unassigned classes.
         */
        void onDatePressed(final Context context, final String date, final CharSequence[] unassigned);

        /**
         * Allows the list view to be filled with incoming data
         */
        void initializeListView(ListView listView);

    }
}
