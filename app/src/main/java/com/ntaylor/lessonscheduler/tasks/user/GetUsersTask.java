package com.ntaylor.lessonscheduler.tasks.user;

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

    private String orgId;
    private UsersDao usersDao;
    private AssignmentsDao assignmentsDao;

    public GetUsersTask(String orgId, UsersDao usersDao, AssignmentsDao assignmentsDao){
        this.orgId = orgId;
        this.usersDao = usersDao;
        this.assignmentsDao = assignmentsDao;
    }

    @Override
    protected List<User> doInBackground(Void... voids) {
        // Get all users in the organization
        List<User> users = usersDao.getUsersByOrg(orgId);
        boolean updateNeeded = false;

        // Foreach user, check if the next_class date has passed
        for (User user : users){
            String next = user.getNextClass();

            if (next != null){
                SimpleDate nextDate = SimpleDate.deserializeDate(next, true);

                // If nextDate has already passed (i.e. nextDate < today)
                if (nextDate.isBefore(new SimpleDate())){

                    // Update is necessary: update last_class and check for future assignments
                    updateNeeded = true;
                    user.setLastClass(next);

                    // Get future assignments for this user
                    List<Assignment> futureClasses = assignmentsDao.getFutureAssignmentsByTeacher(orgId,
                            user.getUserId(), (new SimpleDate()).serializeDate());

                    // Assign a new next_class
                    if (futureClasses.size() > 0){
                        user.setNextClass(futureClasses.get(0).getDate());
                    }
                    else {
                        user.setNextClass(null);
                    }

                    // Update
                    usersDao.update(user);
                }
            }
            else {
                SimpleDate today = new SimpleDate();
                // Check if the next assignment needs to be updated
                List<Assignment> futureClasses = assignmentsDao.getFutureAssignmentsByTeacher(orgId,
                        user.getUserId(), today.serializeDate());

                // Assign a new next_class
                if (futureClasses.size() > 0){
                    user.setNextClass(futureClasses.get(0).getDate());
                }
            }
        }

        if (updateNeeded){
            users = usersDao.getUsersByOrg(orgId);
        }

        return users;
    }

    @Override
    protected void onPostExecute(List<User> users){
        DataProviderFactory.getDataProviderInstance().updateUsers(users);
    }
}
