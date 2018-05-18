package com.ntaylor.lessonscheduler.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ntaylor.lessonscheduler.room.entities.Teacher;

import java.util.List;

@Dao
public interface TeachersDao {

    @Insert
    void insert(Teacher teacher);

    @Query("DELETE FROM teachers WHERE teacher_id LIKE :teacherId")
    int deleteByTeacherId(String teacherId);

    @Query("SELECT * FROM teachers WHERE teacher_id LIKE :teacherId")
    List<Teacher> getTeacherById(String teacherId);

    @Query("DELETE FROM teachers")
    void deleteAll();

}
