package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "organizations", primaryKeys = {"org_id", "org_name"})
public class Organization {

    @NonNull
    @ColumnInfo (name = "org_id")
    private String orgId;

    @NonNull
    @ColumnInfo (name = "org_name")
    private String orgName;

    public Organization(String orgName){
        this.orgId = UUID.randomUUID().toString();
        this.orgName = orgName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
