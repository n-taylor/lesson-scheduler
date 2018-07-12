package com.ntaylor.lessonscheduler.presenters;

import android.accounts.Account;
import android.content.Context;
import android.content.Intent;

import com.ntaylor.lessonscheduler.activities.AccountActivity;
import com.ntaylor.lessonscheduler.activities.ClassesActivity;
import com.ntaylor.lessonscheduler.activities.UpcomingActivity;

public abstract class Presenter {

    /**
     * Opens the account info activity
     * @param context
     */
    public void onAccountItemPressed(Context context){
        Intent intent = new Intent(context, AccountActivity.class);
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
}
