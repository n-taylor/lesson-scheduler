package com.ntaylor.lessonscheduler.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ntaylor.lessonscheduler.room.entities.Classes;

@Dao
public interface ClassesDao {

    @Insert
    void insert(Classes classes);

    @Query("DELETE FROM classes")
    void deleteAll();
}
