package com.ntaylor.lessonscheduler.tasks.aws.classroom;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class CreateClassroomTask extends AsyncTask<Void, Void, Void> {

    private String classname;
    private String orgId;
    private ClassesDao dao;

    public CreateClassroomTask(String className, String orgId){
        this.classname = className;
        this.orgId = orgId;
        this.dao = new ClassesDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.insertClass(new Classroom(orgId, classname));
        return null;
    }

    @Override
    protected void onPostExecute(Void non){
        DataProviderFactory.getDataProviderInstance().fetchClasses();
    }
}
