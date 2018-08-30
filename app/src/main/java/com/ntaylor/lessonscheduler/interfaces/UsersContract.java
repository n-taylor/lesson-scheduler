package com.ntaylor.lessonscheduler.interfaces;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

public interface UsersContract {
    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter {
        /**
         * Initializes the recycler view and sets its manager.
         * @param recyclerView The view to initialize
         */
        void initializeRecyclerView(Context context, RecyclerView recyclerView);

        /**
         * Opens the AccountActivity to create a new user.
         */
        public void onAddUserPressed();
    }
}
