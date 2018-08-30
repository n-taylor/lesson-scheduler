package com.ntaylor.lessonscheduler.interfaces;

import android.content.Context;

public interface ClassroomEditContract {
    interface View extends BaseView {
        /**
         * @return The current text of the classroom name EditText, or null if not initialized
         */
        String getNameText();

        /**
         * Sets the text of the classroom name EditText
         */
        void setNameText(String text);

        /**
         * @return The current text of the classroom description EditText, or null if not initialized
         */
        String getDescriptionText();

        /**
         * Sets the text of the classroom description EditText
         */
        void setDescriptionText(String text);

    }

    interface Presenter extends BasePresenter {
        /**
         * To be called when the delete button is pressed. Removes the classroom from the db.
         */
        public void onCancelPressed(Context context);

        /**
         * To be called when the confirm button is pressed. Updates or adds the classroom to the db.
         * @param context The activity context
         */
        public void onConfirmClicked(Context context);
    }
}
