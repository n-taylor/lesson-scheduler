package com.ntaylor.lessonscheduler.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

public abstract class RoomTask<P, U, T> extends AsyncTask<P, U, T> {

    protected void showToast(String message, Context context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
