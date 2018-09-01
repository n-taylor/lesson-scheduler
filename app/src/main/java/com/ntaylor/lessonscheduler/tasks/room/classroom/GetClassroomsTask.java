package com.ntaylor.lessonscheduler.tasks.room.classroom;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.ClassesDao;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

/**
 * Retrieves the list of classes by a given ID. The list should be only 1 element long when
 * retrieved with a class ID (when byClassId == true && the database contains the class ID).
 *
 */
public class GetClassroomsTask extends AsyncTask<Void, Void, List<Classroom>> {

    private String id;
    private boolean byClassId;
    private ClassesDao dao;

    /**
     *
     * @param id When byClassId == true, this must be the ID of the desired class. Otherwise, the org ID.
     * @param byClassId Whether to retrieve the classes by classID or orgID
     * @param dao The DAO to access the database
     */
    public GetClassroomsTask(String id, boolean byClassId, ClassesDao dao){
        this.id = id;
        this.byClassId = byClassId;
        this.dao = dao;
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
