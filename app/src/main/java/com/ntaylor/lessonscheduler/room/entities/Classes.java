package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "classes",
        foreignKeys = @ForeignKey(entity = Organizations.class, parentColumns = "org_id", childColumns = "org_id", onDelete = CASCADE))
public class Classes {

    @PrimaryKey
    @ColumnInfo (name = "class_id")
    private String classId;

    @ColumnInfo(name = "org_id")
    private String orgId;

    @ColumnInfo(name = "class_name")
    private String className;

    public Classes(String classId, String orgId, String className){
        this.orgId = orgId;
        this.classId = classId;
        this.className = className;
    }

    // GETTERS AND SETTERS ************************************************************************

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }


}
