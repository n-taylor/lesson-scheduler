package com.ntaylor.lessonscheduler.tasks.room.user;

import android.os.AsyncTask;

import com.ntaylor.lessonscheduler.room.dao.AssignmentsDao;
import com.ntaylor.lessonscheduler.room.dao.UsersDao;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.List;

/**
 * Retrieves all the users associated with the current user's organization.
 * For each user, updates the last_class date and next_class date if necessary.
 */
public class GetUsersTask extends AsyncTask<Void, Void, List<User>> {

    private static final int past_multiplier = 4;

    private String orgId;
    private UsersDao usersDao;
    private AssignmentsDao assignmentsDao;

    public GetUsersTask(String orgId, UsersDao usersDao, AssignmentsDao assignmentsDao){
        this.usersDao = usersDao;
        this.orgId = orgId;
        this.assignmentsDao = assignmentsDao;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        SimpleDate today = new SimpleDate();

        // Get all users in the organization
        List<User> users = usersDao.getUsersByOrg(orgId);

        // Get some past and all future assignments for the organization
        List<Assignment> futures = assignmentsDao.getFutureAssignmentByOrganization(orgId, today.serializeDate());
        List<Assignment> pasts = assignmentsDao.getPastAssignmentsByOrganization(orgId, today.serializeDate(), users.size()*past_multiplier);


        // Foreach user, find the next and last assignments and update them in the user's columns if necessary
        for (User user : users){
            boolean updateNeeded = false;

            // Find the latest past assignment taught by 'user'
            String lastAssignment = null;
            for (Assignment assignment : pasts){
                if (assignment.getTeacherId().equals(user.getUserId()) && (lastAssignment == null || assignment.getDate().compareTo(lastAssignment) > 0)){
                    lastAssignment = assignment.getDate();
                }
            }

            // Compare it with the teacher's last listed assignment
            String last = user.getLastClass();
            if ((last == null && lastAssignment != null) || (last != null && !last.equals(lastAssignment))){
                updateNeeded = true;
                user.setLastClass(lastAssignment);
            }

            // Find the earliest future assignment taught by 'user'
            String nextAssignment = null;
            for (Assignment assignment : futures){
                // If the assignment is for the current user && [nextAssignment is null or nextAssignment is earlier than the previously found]
                if (assignment.getTeacherId().equals(user.getUserId()) && (nextAssignment == null || assignment.getDate().compareTo(nextAssignment) < 0)){
                    nextAssignment = assignment.getDate();
                }
            }

            // Compare it with the user's listed next assignment
            String next = user.getNextClass();
            if ((next == null && nextAssignment != null) || (next != null && !next.equals(nextAssignment))){
                updateNeeded = true;
                user.setNextClass(nextAssignment);
            }

            if (updateNeeded){
                usersDao.update(user);
            }
        }

        return users;
    }

    @Override
    protected void onPostExecute(List<User> users){
        DataProviderFactory.getDataProviderInstance().updateUsers(users);
    }
}
