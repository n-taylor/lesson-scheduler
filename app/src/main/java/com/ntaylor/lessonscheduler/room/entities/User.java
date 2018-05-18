package com.ntaylor.lessonscheduler.room.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;

import java.util.UUID;

import android.arch.persistence.room.Index;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity (tableName = "users", primaryKeys = {"user_id", "user_name"},
        foreignKeys = @ForeignKey(entity = Organization.class, parentColumns = "org_id", childColumns = "org_id", onDelete = CASCADE),
        indices = {@Index(value = {"user_id", "user_name"}, unique = true), @Index("org_id")})
public class User {

    @NonNull
    @ColumnInfo(name = "user_id")
    private String userId;

    @NonNull
    @ColumnInfo(name = "user_name")
    private String userName;

    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @NonNull
    @ColumnInfo(name = "org_id")
    private String orgId;

    public User(@NonNull String userName, @NonNull String name, @NonNull String orgId){
        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
        this.name = name;
        this.orgId = orgId;
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

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(@NonNull String orgId) {
        this.orgId = orgId;
    }

}
