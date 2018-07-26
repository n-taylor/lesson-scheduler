package com.ntaylor.lessonscheduler.presenters;


import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.room.database.LessonsRoomDatabase;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class SignUpPresenter {

    private static final String EMPTY_TEXT_MESSAGE = "Please enter a user name and organization";
    private static final String DATA_PROVIDER_ERROR = "There was a problem with the data provider";

    private View fragment;

    public SignUpPresenter(View fragment){
        this.fragment = fragment;
    }

    /**
     * This should be called when the user has entered their information to log in or sign up.
     */
    public void onButtonPressed(Activity activity, String userName, String org){
        if (userName == null || userName.isEmpty() || org == null || org.isEmpty()){
            Snackbar.make(activity.findViewById(R.id.sign_up_activity_id), EMPTY_TEXT_MESSAGE, Snackbar.LENGTH_SHORT).show();
            return;
        }

        DataProvider dp =  DataProviderFactory.initializeDataProvider(activity, DataProviderFactory.ROOM_PROVIDER);
        if (dp != null){
            dp.createUser(activity, userName, org);
        }
        else
        {
            Snackbar.make(activity.findViewById(R.id.sign_up_activity_id), DATA_PROVIDER_ERROR, Snackbar.LENGTH_SHORT).show();
        }
    }


    public interface View {
        void destroySelf();
    }
}
