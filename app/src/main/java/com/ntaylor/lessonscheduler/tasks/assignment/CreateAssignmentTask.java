package com.ntaylor.lessonscheduler.tasks.assignment;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.SimpleDate;

public class CreateAssignmentTask extends AsyncTask<Void, Void, Void> {

    private String orgId;
    private String classId;
    private String teacherId;
    private SimpleDate date;
    private AssignmentsDao dao;

    public CreateAssignmentTask(String orgId, String classId, String teacherId, SimpleDate date, AssignmentsDao dao){
        this.orgId = orgId;
        this.classId = classId;
        this.teacherId = teacherId;
        this.date = date;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.insert(new Assignment(date.serializeDate(), orgId, classId, teacherId));
        return null;
    }

    @Override
    protected void onPostExecute(Void voids){
        DataProviderFactory.getDataProviderInstance().fetchAssignments();
    }
}
