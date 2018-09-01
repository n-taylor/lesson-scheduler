package com.ntaylor.lessonscheduler.tasks.aws.assignment;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.List;

public class GetFutureAssignmentsTask extends AsyncTask<Void, Void, List<Assignment>> {

    private String orgId;
    private AssignmentsDao dao;
    private String today;

    public GetFutureAssignmentsTask(String orgId){
        this.orgId = orgId;
        today = new SimpleDate().serializeDate();
        this.dao = new AssignmentsDao();
    }

    @Override
    protected List<Assignment> doInBackground(Void... voids) {
        return dao.getFutureAssignmentByOrg(orgId, today);
    }

    @Override
    protected void onPostExecute(List<Assignment> assignments){
        DataProviderFactory.getDataProviderInstance().updateAssignments(assignments);
    }
}
