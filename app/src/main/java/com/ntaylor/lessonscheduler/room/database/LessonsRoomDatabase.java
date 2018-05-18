package com.ntaylor.lessonscheduler.room.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.dao.OrganizationsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Class;
import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;

@Database(entities = {Class.class, Assignment.class, Organization.class, User.class}, version = 1, exportSchema = false)
public abstract class LessonsRoomDatabase extends RoomDatabase{

    private static LessonsRoomDatabase INSTANCE;

    public abstract ClassesDao getClassesDao();
    public abstract AssignmentsDao getAssignmentsDao();
    public abstract OrganizationsDao getOrganizationsDao();
    public abstract UsersDao getUsersDao();

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
