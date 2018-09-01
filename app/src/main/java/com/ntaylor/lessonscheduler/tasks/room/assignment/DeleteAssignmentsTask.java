package com.ntaylor.lessonscheduler.tasks.room.assignment;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;

public class DeleteAssignmentsTask extends AsyncTask<Void, Void, Integer> {

    public static final int ALL_CLASSES = 0;
    public static final int BY_CLASS_ID = 1;
    public static final int BY_TEACHER = 2;
    public static final int BY_ORG = 3;

    private int filter;
    private String orgId;
    private String id;
    private AssignmentsDao dao;

    public DeleteAssignmentsTask(String id, int filter, AssignmentsDao dao){
        this.id = id;
        this.filter = filter;
        this.dao = dao;
    }

    public DeleteAssignmentsTask(String orgId, String id, int filter, AssignmentsDao dao){
        this.orgId = orgId;
        this.id = id;
        this.filter = filter;
        this.dao = dao;
    }


    @Override
    protected Integer doInBackground(Void... voids) {
        int deleted = -1;

        if (filter == ALL_CLASSES){
            deleted = dao.deleteAll();
        }
        else if (filter == BY_CLASS_ID){
            deleted = dao.deleteByClass(id);
        }
        else if (filter == BY_ORG){
            deleted = dao.deleteByOrganization(id);
        }
        else if (filter == BY_TEACHER){
            deleted = dao.deleteByTeacher(orgId, id);
        }

        return deleted;
    }

    @Override
    protected void onPostExecute(Integer deleted){
        System.out.println(deleted + " assignments deleted");
    }
}
