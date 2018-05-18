package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "organizations")
public class Organizations {

    @PrimaryKey
    @ColumnInfo (name = "org_id")
    private String orgId;

    @ColumnInfo (name = "org_name")
    private String orgName;

    public Organizations(String orgId, String orgName){
        this.orgId = orgId;
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
