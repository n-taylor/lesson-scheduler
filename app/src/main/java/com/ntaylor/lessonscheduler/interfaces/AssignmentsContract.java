package com.ntaylor.lessonscheduler.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.ntaylor.lessonscheduler.room.entities.Assignment;

public interface AssignmentsContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        /**
         * Initializes the RecyclerView and assigns it a layout manager.
         */
        public void initializeRecycler(Context context, RecyclerView recyclerView);

        /**
         * Confirms the users wants to delete the assignment, then requests the data provider to
         * delete it.
         * @param assignment The assignment to delete.
         */
        public void onDeletePressed(final Assignment assignment);
    }
}
