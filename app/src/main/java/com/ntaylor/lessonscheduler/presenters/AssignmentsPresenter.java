package com.ntaylor.lessonscheduler.presenters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ntaylor.lessonscheduler.assignments.AssignmentsAdapter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.HashMap;
import java.util.List;

public class AssignmentsPresenter extends Presenter {

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
    public void onDeletePressed(Assignment assignment){

    }

    // Overrides ===========================================================================

    @Override
    public void onAssignmentsUpdated(List<Assignment> assignments){
        if (assignments != null && users != null && classes != null){
            AssignmentsAdapter adapter = new AssignmentsAdapter(assignments, mapClassIds(classes), mapUserIds(users), this);
        }
    }

    @Override
    public void onUsersUpdated(List<User> users){
        if (assignments != null && users != null && classes != null){
            AssignmentsAdapter adapter = new AssignmentsAdapter(assignments, mapClassIds(classes), mapUserIds(users), this);
        }
    }

    @Override
    public void onClassesUpdated(List<Classroom> classes){
        if (assignments != null && users != null && classes != null){
            AssignmentsAdapter adapter = new AssignmentsAdapter(assignments, mapClassIds(classes), mapUserIds(users), this);
        }
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
