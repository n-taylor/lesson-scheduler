package com.ntaylor.lessonscheduler.tasks.aws.classroom;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class UpdateClassroomTask extends AsyncTask<Void, Void, Void> {

    private Classroom classroom;
    private ClassesDao dao;

    public UpdateClassroomTask(Classroom classroom){
        this.classroom = classroom;
        this.dao = new ClassesDao();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.updateClassroom(classroom);
        return null;
    }

    @Override
    protected void onPostExecute(Void voids){
        DataProviderFactory.getDataProviderInstance().fetchClasses();
    }
}
