package com.ntaylor.lessonscheduler.tasks.room.classroom;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class CreateClassroomTask extends AsyncTask<Void, Void, Void> {

    private String name;
    private String orgId;
    private ClassesDao classesDao;


    public CreateClassroomTask(String className, String orgId, ClassesDao classesDao){
        this.name = className;
        this.orgId = orgId;
        this.classesDao = classesDao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Classroom toInsert = new Classroom(orgId, name);
        classesDao.insert(toInsert);
        return null;
    }

    @Override
    protected void onPostExecute(Void non){
        DataProviderFactory.getDataProviderInstance().fetchClasses();
    }
}
