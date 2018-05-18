package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "classes",
        foreignKeys = @ForeignKey(entity = Organization.class, parentColumns = "org_id", childColumns = "org_id", onDelete = CASCADE),
        indices = {@Index("org_id")})
public class Class {

    @PrimaryKey
    @NonNull
    @ColumnInfo (name = "class_id")
    private String classId;

    @NonNull
    @ColumnInfo(name = "org_id")
    private String orgId;

    @NonNull
    @ColumnInfo(name = "class_name")
    private String className;

    public Class(@NonNull String orgId, @NonNull String className){
        this.orgId = orgId;
        this.classId = UUID.randomUUID().toString();
        this.className = className;
    }

    // GETTERS AND SETTERS ************************************************************************

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull String orgId) {
        this.orgId = orgId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(@NonNull String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(@NonNull String classId) {
        this.classId = classId;
    }


}
