package com.ntaylor.lessonscheduler.tasks.room.classroom;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class UpdateClassroomTask extends AsyncTask<Void, Void, Void> {

    private Classroom classroom;
    private ClassesDao dao;

    public UpdateClassroomTask(Classroom classroom, ClassesDao dao){
        this.classroom = classroom;
        this.dao = dao;
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
