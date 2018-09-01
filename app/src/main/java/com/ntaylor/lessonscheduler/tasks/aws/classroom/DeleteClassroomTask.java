package com.ntaylor.lessonscheduler.tasks.aws.classroom;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class DeleteClassroomTask extends AsyncTask<Void, Void, Void> {

    private String classId;
    private ClassesDao dao;

    public DeleteClassroomTask(Classroom classroom){
        this.classId = classroom.getClassId();
        this.dao = new ClassesDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.deleteClassroom(classId);
        return null;
    }

    @Override
    protected void onPostExecute(Void nothing){
        DataProviderFactory.getDataProviderInstance().onClassroomDeleted();
        DataProviderFactory.getDataProviderInstance().fetchClasses();
    }
}
