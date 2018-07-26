package com.ntaylor.lessonscheduler.presenters;

import android.accounts.Account;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ntaylor.lessonscheduler.activities.AccountActivity;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.users.UsersAdapter;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.ArrayList;
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

    /**
     * Opens the AccountActivity to create a new user.
     */
    public void onAddUserPressed(){
        Intent intent = new Intent((Activity)activity, AccountActivity.class);
        ((Activity)activity).startActivity(intent);
    }

    /**
     * Opens the AccountActivity to display or edit the user's information
     * @param user The user about whom to display information.
     */
    public void onTeacherPressed(User user){
        Intent intent = new Intent((Activity)activity, AccountActivity.class);
        intent.putExtra(AccountActivity.EXTRA_USER_ID, user.getUserId());
        intent.putExtra(AccountActivity.EXTRA_USER_NAME, user.getUserName());
        ((Activity)activity).startActivity(intent);
    }

    // Overrides ==========================================================================

    /**
     * Populates the recycler view with an adapter containing the list of users.
     * @param users The current list of teachers in the user's organization.
     */
    @Override
    public void onUsersUpdated(List<User> users){
        // Remove the current user's name from the list
        ArrayList<User> temp = new ArrayList<>(users);
        for (User user : temp){
            if (user.getUserId().equals(UserInfo.getUserInfo().getUserId())){
                users.remove(user);
            }
        }

        // Initialize the adapter
        UsersAdapter adapter = new UsersAdapter(this, users);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onUserCreationAttempted(boolean success, String name){
        provider.fetchUsers();
    }

    @Override
    public void onUserNameChanged(boolean success, String name){
        provider.fetchUsers();
    }

    // private methods ====================================================================

    public interface UsersView {

        void destroySelf();

    }
}
