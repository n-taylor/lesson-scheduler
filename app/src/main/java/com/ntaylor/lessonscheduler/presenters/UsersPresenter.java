package com.ntaylor.lessonscheduler.presenters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

public class UsersPresenter extends Presenter implements DataObserver {

    private DataProvider provider;
    private UsersView activity;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;

    public UsersPresenter(UsersView activity){
        this.activity = activity;
        this.provider = DataProviderFactory.getDataProviderInstance();
        provider.addObserver(this);
    }

    // public methods =====================================================================

    public void initializeRecyclerView(Context context, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.recyclerView.setHasFixedSize(true);

        // linear layout manager
        manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);

        // fetch users
        provider.fetchUsers();
    }

    // Overrides ==========================================================================

    /**
     * Populates the recycler view with an adapter containing the list of users.
     * @param users The current list of teachers in the user's organization.
     */
    @Override
    public void onUsersUpdated(List<User> users){
        
    }

    // private methods ====================================================================

    public interface UsersView {

        void destroySelf();

    }
}
