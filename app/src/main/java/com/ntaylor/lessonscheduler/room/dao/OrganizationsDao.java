package com.ntaylor.lessonscheduler.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ntaylor.lessonscheduler.room.entities.Organization;

import java.util.List;

@Dao
public interface OrganizationsDao {

    @Insert
    void insert(Organization organization);

    @Query("SELECT * FROM organizations WHERE org_id LIKE :orgId")
    List<Organization> getOrganizationById(String orgId);

    @Query("SELECT * FROM organizations WHERE org_name LIKE :name")
    List<Organization> getOrganizationByName(String name);

    @Query("DELETE FROM organizations WHERE org_id LIKE :orgId")
    int deleteOrganizationById(String orgId);

    @Query("DELETE FROM organizations WHERE org_name LIKE :name")
    int deleteOrganizationByName(String name);

    @Query("DELETE FROM organizations")
    void deleteAll();
}
