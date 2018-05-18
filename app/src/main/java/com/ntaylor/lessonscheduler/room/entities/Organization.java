package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

import android.support.annotation.NonNull;

@Entity(tableName = "organizations", primaryKeys = {"org_id", "org_name"},
        indices = {@Index(value = "org_id", unique = true)})
public class Organization {

    @NonNull
    @ColumnInfo (name = "org_id")
    private String orgId;

    @NonNull
    @ColumnInfo (name = "org_name")
    private String orgName;

    public Organization(@NonNull String orgName){
        this.orgId = UUID.randomUUID().toString();
        this.orgName = orgName;
    }

    @NonNull
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull String orgId) {
        this.orgId = orgId;
    }

    @NonNull
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(@NonNull String orgName) {
        this.orgName = orgName;
    }
}
