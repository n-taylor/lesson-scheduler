package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.UUID;

import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ntaylor.lessonscheduler.util.AuthLevel;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "users",
        foreignKeys = @ForeignKey(entity = Organization.class, parentColumns = "org_id", childColumns = "org_id", onDelete = CASCADE),
        indices = {@Index("org_id"), @Index(value = "user_id", unique = true)})
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    private String userId;

    @NonNull
    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "org_id")
    private String orgId;

    @ColumnInfo(name = "last_class")
    private String lastClass;

    @ColumnInfo(name = "next_class")
    private String nextClass;

    @ColumnInfo(name = "auth_level")
    private int authLevel;

    public User(@NonNull String userName, String orgId){
        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
        this.orgId = orgId;
        if (orgId == null) {
            this.authLevel = AuthLevel.NO_ORG;
        }
        else {
            this.authLevel = AuthLevel.TEACHER;
        }
    }

    @NonNull
    public String getUserName(){
        return userName;
    }

    public void setUserName(@NonNull String userName){
        this.userName = userName;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull String orgId) {
        this.orgId = orgId;
    }

    public String getLastClass() { return this.lastClass; }

    public void setLastClass(String lastClass) { this.lastClass = lastClass; }

    public String getNextClass() { return this.nextClass; }

    public void setNextClass(String nextClass) { this.nextClass = nextClass; }

    public int getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(int authLevel) {
        this.authLevel = authLevel;
    }

}
