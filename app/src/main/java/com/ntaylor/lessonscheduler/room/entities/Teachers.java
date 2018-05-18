package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "teachers",
        foreignKeys = @ForeignKey(entity = Organizations.class, parentColumns = "org_id", childColumns = "org_id", onDelete = CASCADE))
public class Teachers {

    @PrimaryKey
    @ColumnInfo (name = "teacher_id")
    private String teacherId;

    @ColumnInfo (name = "org_id")
    private String orgId;

    @ColumnInfo (name = "name")
    private String name;

    public Teachers (String teacherId, String orgId, String name){
        this.orgId = orgId;
        this.name = name;
    }

    public String teacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
