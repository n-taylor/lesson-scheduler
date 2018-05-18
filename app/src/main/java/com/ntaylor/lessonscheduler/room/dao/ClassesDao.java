package com.ntaylor.lessonscheduler.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ntaylor.lessonscheduler.room.entities.Class;

import java.util.List;

@Dao
public interface ClassesDao {

    @Insert
    void insert(Class classes);

    @Query("DELETE FROM classes")
    void deleteAll();

    @Query("SELECT * FROM classes WHERE class_id LIKE :classId")
    List<Class> getClassesById(String classId);

    @Query("SELECT * FROM classes WHERE org_id LIKE :orgId")
    List<Class> getClassesByOrg(String orgId);
}
