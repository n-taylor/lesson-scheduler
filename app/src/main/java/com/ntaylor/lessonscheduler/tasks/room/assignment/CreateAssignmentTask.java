package com.ntaylor.lessonscheduler.tasks.room.assignment;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.List;

/**
 * If an assignment does not exist for the given date and class, creates one.
 * If an assignment already exists, updates it.
 */
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
        // check if an assignment already exists for this date and class
        List<Assignment> previous = dao.getClassAssignmentByDate(orgId, classId, date.serializeDate());
        if (previous.size() > 0){
            // update the assignment
            Assignment toUpdate = previous.get(0);
            toUpdate.setTeacherId(teacherId);
            dao.updateAssignment(toUpdate);
        }
        else {
            // create a new assignment
            dao.insert(new Assignment(date.serializeDate(), orgId, classId, teacherId));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void voids){
        DataProviderFactory.getDataProviderInstance().fetchAssignments();
    }
}
