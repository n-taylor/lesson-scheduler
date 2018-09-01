package com.ntaylor.lessonscheduler.tasks.room.classroom;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

/**
 * Deletes the given classroom from the DB and fetches the updated classes list for the organization
 */
public class DeleteClassroomTask extends AsyncTask<Void, Void, Void> {

    private Classroom classroom;
    private ClassesDao dao;

    public DeleteClassroomTask(Classroom classroom, ClassesDao dao){
        this.classroom = classroom;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.deleteClassroom(classroom.getClassId());
        return null;
    }

    @Override
    protected void onPostExecute(Void nothing){
        DataProviderFactory.getDataProviderInstance().onClassroomDeleted();
        DataProviderFactory.getDataProviderInstance().fetchClasses();
    }
}
