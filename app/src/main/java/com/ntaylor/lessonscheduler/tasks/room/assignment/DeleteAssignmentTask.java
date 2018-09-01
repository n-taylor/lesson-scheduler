package com.ntaylor.lessonscheduler.tasks.room.assignment;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class DeleteAssignmentTask extends AsyncTask<Void, Void, Boolean> {

    private Assignment assignment;
    private AssignmentsDao dao;

    public DeleteAssignmentTask(Assignment assignment, AssignmentsDao dao){
        this.assignment = assignment;
        this.dao = dao;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        int deleted = dao.delete(assignment.getIndex());
        return deleted > 0;
    }

    @Override
    protected void onPostExecute(Boolean success){
        DataProviderFactory.getDataProviderInstance().onAssignmentDeleted(success);
        DataProviderFactory.getDataProviderInstance().fetchAssignments();
    }
}
