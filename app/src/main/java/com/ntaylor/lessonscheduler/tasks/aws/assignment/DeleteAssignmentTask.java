package com.ntaylor.lessonscheduler.tasks.aws.assignment;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class DeleteAssignmentTask extends AsyncTask<Void, Void, Boolean> {

    private int index;
    private AssignmentsDao dao;

    public DeleteAssignmentTask(Assignment assignment){
        index = assignment.getIndex();
        dao = new AssignmentsDao();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        return dao.delete(index);
    }

    @Override
    protected void onPostExecute(Boolean success){
        DataProviderFactory.getDataProviderInstance().onAssignmentDeleted(success);
        DataProviderFactory.getDataProviderInstance().fetchAssignments();
    }
}
