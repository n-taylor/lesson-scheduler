package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.UUID;

import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "teachers",
        foreignKeys = @ForeignKey(entity = Organization.class, parentColumns = "org_id", childColumns = "org_id", onDelete = CASCADE),
        indices = {@Index("org_id")})
public class Teacher {

    @PrimaryKey
    @android.support.annotation.NonNull
    @ColumnInfo (name = "teacher_id")
    private String teacherId;

    @NonNull
    @ColumnInfo (name = "org_id")
    private String orgId;

    @NonNull
    @ColumnInfo (name = "name")
    private String name;

    public Teacher(String orgId, String name){
        this.teacherId = UUID.randomUUID().toString();
        this.orgId = orgId;
        this.name = name;
    }

    @NonNull
    public String teacherId() {
        return teacherId;
    }

    public void setTeacherId(@NonNull String teacherId) {
        this.teacherId = teacherId;
    }

    @NonNull
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull String orgId) {
        this.orgId = orgId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }
}
