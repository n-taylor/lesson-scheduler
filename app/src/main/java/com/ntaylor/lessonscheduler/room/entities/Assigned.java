package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.v4.content.res.FontResourcesParserCompat;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "assigned",
        foreignKeys = {@ForeignKey(entity = Organizations.class, parentColumns = "org_id", childColumns = "org_id", onDelete = CASCADE),
        @ForeignKey(entity = Teachers.class, parentColumns = "teacher_id", childColumns = "teacher_id", onDelete = CASCADE),
        @ForeignKey(entity = Classes.class, parentColumns = "class_id", childColumns = "class_id", onDelete = CASCADE)})
public class Assigned {

    @PrimaryKey (autoGenerate = true)
    private int index;

    @ColumnInfo (name = "date")
    private String date;

    @ColumnInfo (name = "org_id")
    private String orgId;

    @ColumnInfo (name = "class_id")
    private String classId;

    @ColumnInfo (name = "teacher_id")
    private String teacherId;

    public Assigned (String date, String orgId, String classId, String teacherId){
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getOrgId(){
        return this.orgId;
    }

    public void setOrgId(String orgId){
        this.orgId = orgId;
    }
}
