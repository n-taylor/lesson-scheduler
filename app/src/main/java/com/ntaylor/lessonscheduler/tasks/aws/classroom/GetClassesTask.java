package com.ntaylor.lessonscheduler.tasks.aws.classroom;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

public class GetClassesTask extends AsyncTask<Void, Void, List<Classroom>> {

    private String id;
    private boolean byClassId;
    private ClassesDao dao;

    /**
     * @param id When byClassId == true, this must be the ID of the desired class. Otherwise, the org ID.
     * @param byClassId Whether to retrieve the classes by classID or orgID
     */
    public GetClassesTask(String id, boolean byClassId){
        this.id = id;
        this.byClassId = byClassId;
        this.dao = new ClassesDao();
    }

    @Override
    protected List<Classroom> doInBackground(Void... voids) {
        List<Classroom> classes = null;
        if (byClassId){
            classes = dao.getClassesById(id);
        }
        else{
            classes = dao.getClassesByOrg(id);
        }
        return classes;
    }

    @Override
    protected void onPostExecute(List<Classroom> classes){
        DataProviderFactory.getDataProviderInstance().updateClasses(classes);
    }
}
