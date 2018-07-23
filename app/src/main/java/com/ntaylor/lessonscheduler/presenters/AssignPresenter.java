package com.ntaylor.lessonscheduler.presenters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

public class AssignPresenter extends Presenter {

    private AssignView view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private DataProvider provider;

    public AssignPresenter(AssignView activity){
        this.view = activity;
        this.provider = DataProviderFactory.getDataProviderInstance();
        provider.addObserver(this);
    }

    public void initializeRecyclerView(Context context, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.recyclerView.setHasFixedSize(true);

        // linear layout manager
        manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);

        // fetch users
        provider.fetchUsers();
    }

    /**
     * To be called when the list of users in an organization has been updated.
     * Initialize the recyclerView adapter
     * @param users The current list of teachers in the user's organization.
     */
    @Override
    public void onUsersUpdated(List<User> users) {

    }

    public interface AssignView {
        void destroySelf();
    }
}
