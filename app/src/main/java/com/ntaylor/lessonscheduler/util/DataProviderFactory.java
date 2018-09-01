package com.ntaylor.lessonscheduler.util;

import android.app.Activity;
import android.app.Application;

public abstract class DataProviderFactory {

    public static final int ROOM_PROVIDER = 0;
    public static final int AWS_PROVIDER = 1;

    private static final int PROVIDER = AWS_PROVIDER;
    private static DataProvider roomRepository;

    public static DataProvider getDataProviderInstance(){
        return roomRepository;
    }

    /**
     * This method MUST be called before getDataProviderInstance(). Otherwise, the DataProvider returned will be null.
     * This method initializes the DataProvider so it can be accessible without a Context.
     *
     * @param activity Required to provide access to the database.
     * @return The data provider initialized.
     */
    public static DataProvider initializeDataProvider(Activity activity){
        switch(PROVIDER){
            case ROOM_PROVIDER:
                if (roomRepository == null)
                    roomRepository =  new RoomRepository(activity);
                return roomRepository;
            case AWS_PROVIDER:
                if (roomRepository == null)
                    roomRepository = new AwsRepository();
                return roomRepository;
            default:
                return null;
        }
    }
}
