package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "assignments",
        foreignKeys = {@ForeignKey(entity = Organization.class, parentColumns = "org_id", childColumns = "org_id", onDelete = CASCADE),
        @ForeignKey(entity = User.class, parentColumns = "user_id", childColumns = "teacher_id", onDelete = CASCADE),
        @ForeignKey(entity = Class.class, parentColumns = "class_id", childColumns = "class_id", onDelete = CASCADE)},
        indices = {@Index("org_id"), @Index("teacher_id"), @Index("class_id")})
public class Assignment {

    @PrimaryKey (autoGenerate = true)
    private int index;

    @NonNull
    @ColumnInfo (name = "date")
    private String date;

    @NonNull
    @ColumnInfo (name = "org_id")
    private String orgId;

    @NonNull
    @ColumnInfo (name = "class_id")
    private String classId;

    @NonNull
    @ColumnInfo (name = "teacher_id")
    private String teacherId;

    public Assignment(@NonNull String date, @NonNull String orgId, @NonNull String classId, @NonNull String teacherId){
        this.orgId = orgId;
        this.date = date;
        this.classId = classId;
        this.teacherId = teacherId;
    }

    // GETTERS AND SETTERS

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getClassId() {
        return classId;
    }

    public void setClassId(@NonNull String classId) {
        this.classId = classId;
    }

    @NonNull
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(@NonNull String teacherId) {
        this.teacherId = teacherId;
    }

    @NonNull
    public String getOrgId(){
        return this.orgId;
    }

    public void setOrgId(@NonNull String orgId){
        this.orgId = orgId;
    }
}
