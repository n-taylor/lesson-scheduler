package com.ntaylor.lessonscheduler.util;

import android.app.Activity;
import android.app.Application;

public abstract class DataProviderFactory {

    public static final int ROOM_PROVIDER = 0;

    public static DataProvider getDataProviderInstance(Activity activity, int type){
        switch(type){
            case ROOM_PROVIDER:
                return new RoomRepository(activity);
            default:
                  return null;
        }
    }
}
