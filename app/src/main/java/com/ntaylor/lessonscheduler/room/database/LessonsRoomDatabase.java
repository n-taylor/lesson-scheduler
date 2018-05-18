package com.ntaylor.lessonscheduler.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ntaylor.lessonscheduler.room.entities.Classes;
import com.ntaylor.lessonscheduler.room.dao.ClassesDao;

@Database(entities = {Classes.class}, version = 1)
public abstract class LessonsRoomDatabase extends RoomDatabase{

    private static LessonsRoomDatabase INSTANCE;

    public abstract ClassesDao getClasses();

    public static LessonsRoomDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (LessonsRoomDatabase.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LessonsRoomDatabase.class, "lessons_database")
                        .build();
            }
        }
        return INSTANCE;
    }
}
