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
import com.ntaylor.lessonscheduler.util.AuthLevel;

@Database(entities = {Classroom.class, Assignment.class, Organization.class, User.class}, version = 5, exportSchema = false)
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
                        .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
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

    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Add columns last_class and next_class
            database.execSQL("ALTER TABLE users ADD COLUMN last_class TEXT");
            database.execSQL("ALTER TABLE users ADD COLUMN next_class TEXT");
        }
    };

    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("CREATE TABLE users_new (user_id TEXT NOT NULL, user_name TEXT NOT NULL, org_id TEXT NOT NULL, " +
                    "last_class TEXT, next_class TEXT, PRIMARY KEY(user_id), " +
                    "FOREIGN KEY (org_id) REFERENCES organizations(org_id) ON DELETE CASCADE)");

            // Drop and recreate indices
            database.execSQL("DROP INDEX index_users_org_id");
            database.execSQL("DROP INDEX index_users_user_id");

            database.execSQL("CREATE INDEX index_users_org_id ON users_new(org_id)");
            database.execSQL("CREATE UNIQUE INDEX index_users_user_id ON users_new(user_id)");

            // Copy the data
            database.execSQL("INSERT INTO users_new (user_id, user_name, org_id, last_class, next_class) " +
                    "SELECT user_id, user_name, org_id, last_class, next_class FROM users");

            // Remove the old table
            database.execSQL("DROP TABLE users");

            // Rename the new table to the old table name
            database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };

    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Create the new table
            database.execSQL("CREATE TABLE users_new (user_id TEXT NOT NULL, user_name TEXT NOT NULL, org_id TEXT, " +
                    "last_class TEXT, next_class TEXT, auth_level INTEGER NOT NULL DEFAULT 0, PRIMARY KEY(user_id), " +
                    "FOREIGN KEY (org_id) REFERENCES organizations(org_id) ON DELETE CASCADE)");

            // Drop and recreate indices
            database.execSQL("DROP INDEX index_users_org_id");
            database.execSQL("DROP INDEX index_users_user_id");

            database.execSQL("CREATE INDEX index_users_org_id ON users_new(org_id)");
            database.execSQL("CREATE UNIQUE INDEX index_users_user_id ON users_new(user_id)");

            // Copy the data
            database.execSQL("INSERT INTO users_new (user_id, user_name, org_id, last_class, next_class) " +
                    "SELECT user_id, user_name, org_id, last_class, next_class FROM users");

            // Add auth levels to existing users
            database.execSQL("UPDATE users_new SET auth_level = " + AuthLevel.PRESIDENCY);

            // Remove the old table
            database.execSQL("DROP TABLE users");

            // Rename the new table to the old table name
            database.execSQL("ALTER TABLE users_new RENAME TO users");
        }
    };
}
