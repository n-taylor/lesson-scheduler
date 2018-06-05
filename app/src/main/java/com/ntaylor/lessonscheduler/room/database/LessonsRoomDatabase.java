package com.ntaylor.lessonscheduler.room.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.dao.OrganizationsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.entities.Organization;
import com.ntaylor.lessonscheduler.room.entities.User;

@Database(entities = {Classroom.class, Assignment.class, Organization.class, User.class}, version = 2, exportSchema = false)
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
                        .addMigrations(MIGRATION_1_2)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // try doing nothing
        }
    };
}
