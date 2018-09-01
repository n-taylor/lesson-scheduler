package com.ntaylor.lessonscheduler.tasks.room.assignment;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.List;

public class GetAssignmentsTask extends AsyncTask<Void, Void, List<Assignment>> {

    public static final int PAST = 0;
    public static final int ALL = 1;
    public static final int FUTURE = 2;

    public static final int BY_TEACHER_ID = 0;
    public static final int BY_ORG_ID = 1;
    public static final int BY_DATE = 2;
    public static final int BY_CLASS_ID = 3;

    private String orgId;
    private String id; // Class ID, Teacher ID, or date
    private int filter;
    private int timePeriod;
    private AssignmentsDao dao;


    public GetAssignmentsTask(String orgId, String id, int filter, int timePeriod, AssignmentsDao dao){
        this.orgId = orgId;
        this.id = id;
        this.filter = filter;
        this.timePeriod = timePeriod;
        this.dao = dao;
    }

    @Override
    protected List<Assignment> doInBackground(Void... voids) {
        List<Assignment> assignments = null;
        if (filter == BY_TEACHER_ID){
            if (timePeriod == PAST){
                assignments = dao.getPastAssignmentsByTeacher(orgId, id, new SimpleDate().serializeDate());
            }
            else {
                assignments = dao.getFutureAssignmentsByTeacher(orgId, id, new SimpleDate().serializeDate());
            }
        }
        else if (filter == BY_ORG_ID){
            assignments = dao.getFutureAssignmentByOrganization(orgId, new SimpleDate().serializeDate());
        }
        else if (filter == BY_DATE){
            assignments = dao.getAssignmentsByDate(orgId, id);
        }
        else if (filter == BY_CLASS_ID){
            assignments = dao.getAssignmentsByClass(orgId, id);
        }
        else{
            assignments = dao.getAllAssignments();
        }
        return assignments;
    }

    @Override
    protected void onPostExecute(List<Assignment> assignments){
        DataProviderFactory.getDataProviderInstance().updateAssignments(assignments);
    }
}
