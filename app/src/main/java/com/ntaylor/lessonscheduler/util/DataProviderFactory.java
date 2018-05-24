package com.ntaylor.lessonscheduler.util;

import android.app.Activity;
import android.app.Application;

public abstract class DataProviderFactory {

    public static final int ROOM_PROVIDER = 0;
    private static RoomRepository roomRepository;

    public static DataProvider getDataProviderInstance(){
        return roomRepository;
    }

    /**
     * This method MUST be called before getDataProviderInstance(). Otherwise, the DataProvider returned will be null.
     * This method initializes the DataProvider so it can be accessible without a Context.
     *
     * @param activity Required to provide access to the database.
     * @param type The type of database requested. Currently only ROOM_PROVIDER is supported.
     * @return The data provider initialized.
     */
    public static DataProvider initializeDataProvider(Activity activity, int type){
        switch(type){
            case ROOM_PROVIDER:
                if (roomRepository == null)
                    roomRepository =  new RoomRepository(activity);
                return roomRepository;
            default:
                return null;
        }
    }
}
