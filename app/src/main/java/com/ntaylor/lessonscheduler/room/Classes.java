package com.ntaylor.lessonscheduler.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Classes {

    @PrimaryKey
    private int index;

    @ColumnInfo(name = "org_id")
    private String orgId;

    @ColumnInfo(name = "class_name")
    private String className;

}
