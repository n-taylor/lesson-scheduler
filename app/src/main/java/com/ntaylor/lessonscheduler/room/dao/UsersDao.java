package com.ntaylor.lessonscheduler.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.ntaylor.lessonscheduler.room.entities.User;

import java.util.List;

@Dao
public interface UsersDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE user_id LIKE :userId")
    List<User> getUserById(String userId);

    @Query("SELECT * FROM users WHERE org_id LIKE :orgId")
    List<User> getUsersByOrg(String orgId);

    @Query("SELECT * FROM users WHERE user_name LIKE :userName")
    List<User> getUserByUserName(String userName);

    @Update
    void update(User user);

    @Query("DELETE FROM users WHERE user_id LIKE :userId")
    int deleteById(String userId);

    @Query("DELETE FROM users WHERE org_id LIKE :orgId")
    int deleteByOrg(String orgId);

    @Query("DELETE FROM users WHERE user_name LIKE :userName")
    int deleteByUserName(String userName);

    @Delete
    void delete(User user);

    @Query("DELETE FROM users")
    void deleteAll();

}
