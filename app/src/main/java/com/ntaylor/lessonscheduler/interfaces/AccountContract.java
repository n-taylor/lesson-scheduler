package com.ntaylor.lessonscheduler.interfaces;

import android.content.Context;

public interface AccountContract {
    interface View extends BaseView {
        /**
         * Rename the title in the action bar, hide "Last taught" labels
         */
        void showAddUser();

        void setUserText(String text);
        String getUserText();

        void setOrgLabel(String text);
        void setTitle(String text);
        void setLastLesson(String text);
        void setNextLesson(String text);
    }

    interface Presenter extends BasePresenter {
        /**
         * Checks if the username has been changed and, if it has been, determines its availability.
         * Changes the username if available.
         */
        public void onSaveButtonPressed(Context context);

        /**
         * Opens the classes activity
         */
        public void onClassesActionPressed(Context context);

        /**
         * Resets all modified editable fields
         */
        public void onCancelButtonPressed(Context context);


    }
}
