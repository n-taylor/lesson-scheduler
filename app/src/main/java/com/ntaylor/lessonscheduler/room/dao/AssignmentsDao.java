package com.ntaylor.lessonscheduler.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ntaylor.lessonscheduler.room.entities.Assignment;

import java.util.List;

@Dao
public interface AssignmentsDao {

    // Insert ------------------------------------------------

    @Insert
    void insert(Assignment assignment);

    // Select ------------------------------------------------

    @Query("SELECT * FROM assignments WHERE org_id LIKE :orgId AND date LIKE :date")
    List<Assignment> getAssignmentsByDate(String orgId, String date);

    @Query("SELECT * FROM assignments WHERE org_id LIKE :orgId AND  teacher_id LIKE :teacherId AND date < :date")
    List<Assignment> getPastAssignmentsByTeacher(String orgId, String teacherId, String date);

    @Query("SELECT * FROM assignments WHERE org_id LIKE :orgId AND  teacher_id LIKE :teacherId AND date >= :date")
    List<Assignment> getFutureAssignmentsByTeacher(String orgId, String teacherId, String date);

    @Query("SELECT * FROM assignments WHERE org_id LIKE :orgId AND class_id LIKE :classId")
    List<Assignment> getAssignmentsByClass(String orgId, String classId);

    @Query("SELECT * FROM assignments WHERE org_id LIKE :orgId AND date >= :date")
    List<Assignment> getFutureAssignmentByOrganization(String orgId, String date);

    @Query("SELECT * FROM assignments")
    List<Assignment> getAllAssignments();
    
    // Update ------------------------------------------------
    
    @Update
    int updateAssignment(Assignment assignment);
    
    // Delete ------------------------------------------------

    @Query("DELETE FROM assignments WHERE org_id LIKE :orgId AND class_id LIKE :classId AND date LIKE :date")
    int deleteByClassAndDate(String orgId, String classId, String date);

    @Query("DELETE FROM assignments WHERE org_id LIKE :orgId AND teacher_id LIKE :teacherId AND date LIKE :date")
    int deleteByTeacherAndDate(String orgId, String teacherId, String date);

    @Query("DELETE FROM assignments WHERE org_id LIKE :orgId AND teacher_id LIKE :teacherId")
    int deleteByTeacher(String orgId, String teacherId);

    @Query("DELETE FROM assignments WHERE org_id LIKE :orgId")
    int deleteByOrganization(String orgId);

    @Query("DELETE FROM assignments WHERE class_id LIKE :classId")
    int deleteByClass(String classId);

    @Query("DELETE FROM assignments")
    int deleteAll();

}
