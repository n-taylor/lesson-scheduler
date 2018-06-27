package com.ntaylor.lessonscheduler.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ntaylor.lessonscheduler.room.entities.Classroom;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ClassesDao {

    @Insert
    void insert(Classroom classes);

    @Query("DELETE FROM classes")
    void deleteAll();

    @Query("SELECT * FROM classes WHERE class_id LIKE :classId")
    List<Classroom> getClassesById(String classId);

    @Query("SELECT * FROM classes WHERE org_id LIKE :orgId")
    List<Classroom> getClassesByOrg(String orgId);

    @Update(onConflict = REPLACE)
    void updateClassroom(Classroom classroom);

    @Query("DELETE FROM classes WHERE class_id LIKE :classId")
    int deleteClassroom(String classId);
}
