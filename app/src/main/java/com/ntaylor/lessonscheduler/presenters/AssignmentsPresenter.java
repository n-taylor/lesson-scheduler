package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.assignments.AssignmentsAdapter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.HashMap;
import java.util.List;

public class AssignmentsPresenter extends Presenter {

    private static final String delete_title = "Delete Assignment";
    private static final String delete_message = "Are you sure you want to delete this assignment?";
    private static final String yes_delete = "Delete";
    private static final String no_delete = "Cancel";
    private static final String deleted_success = "Assignment successfully deleted";
    private static final String deleted_failure = "Hmm. We weren't able to delete that assignment.";

    private AssignmentsView activity;
    private DataProvider provider;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;

    private List<Assignment> assignments;
    private List<User> users;
    private List<Classroom> classes;

    public AssignmentsPresenter(AssignmentsView activity){
        this.activity = activity;
        this.provider = DataProviderFactory.getDataProviderInstance();
        provider.addObserver(this);

        provider.fetchUsers();
        provider.fetchClasses();
        provider.fetchAssignments();
    }

    // public methods ======================================================================

    /**
     * Initializes the RecyclerView and assigns it a layout manager.
     */
    public void initializeRecycler(Context context, RecyclerView recyclerView){
        this.recycler = recyclerView;

        this.manager = new LinearLayoutManager(context);
        this.recycler.setLayoutManager(this.manager);
    }

    /**
     * Confirms the users wants to delete the assignment, then requests the data provider to
     * delete it.
     * @param assignment The assignment to delete.
     */
    public void onDeletePressed(final Assignment assignment){
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity)activity);
        builder.setTitle(delete_title)
                .setMessage(delete_message)
                .setNegativeButton(no_delete, null)
                .setPositiveButton(yes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        provider.deleteAssignment(assignment);
                    }
                })
                .create()
                .show();
    }

    // Overrides ===========================================================================

    /**
     * Unhooks self from the list of data observers and ends the activity.
     */
    @Override
    protected void end() {
        activity.destroySelf();
    }

    @Override
    public void onAssignmentsUpdated(List<Assignment> assignments){
        this.assignments = assignments;
        if (assignments != null && users != null && classes != null){
            AssignmentsAdapter adapter = new AssignmentsAdapter(assignments, mapClassIds(classes), mapUserIds(users), this);
            recycler.setAdapter(adapter);
        }
    }

    @Override
    public void onUsersUpdated(List<User> users){
        this.users = users;
        if (assignments != null && users != null && classes != null){
            AssignmentsAdapter adapter = new AssignmentsAdapter(assignments, mapClassIds(classes), mapUserIds(users), this);
            recycler.setAdapter(adapter);
        }
    }

    @Override
    public void onClassesUpdated(List<Classroom> classes){
        this.classes = classes;
        if (assignments != null && users != null && classes != null){
            AssignmentsAdapter adapter = new AssignmentsAdapter(assignments, mapClassIds(classes), mapUserIds(users), this);
            recycler.setAdapter(adapter);
        }
    }

    @Override
    public void onAssignmentDeleted(boolean success){
        String message = success ? deleted_success : deleted_failure;
        Toast.makeText((Activity)activity, message, Toast.LENGTH_SHORT).show();
    }

    // private methods ======================================================================

    private HashMap<String, String> mapClassIds(List<Classroom> classrooms){
        HashMap<String, String> ids = new HashMap<>();
        for (Classroom classroom : classrooms){
            ids.put(classroom.getClassId(), classroom.getClassName());
        }
        return ids;
    }

    private HashMap<String, String> mapUserIds(List<User> users){
        HashMap<String, String> ids = new HashMap<>();
        for (User user : users){
            ids.put(user.getUserId(), user.getUserName());
        }
        return ids;
    }

    public interface AssignmentsView {

        void destroySelf();
    }
}
