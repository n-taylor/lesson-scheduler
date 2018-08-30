package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.activities.AccountActivity;
import com.ntaylor.lessonscheduler.interfaces.UsersContract;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.adapters.UsersAdapter;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UsersPresenter extends Presenter implements UsersContract.Presenter {

    private static final String delete_title = "Delete Account";
    private static final String delete_message = "Are you sure you want to delete %s's account?";
    private static final String yes_delete = "Delete";
    private static final String no_delete = "Cancel";
    private static final String delete_success = "User successfully deleted";
    private static final String delete_failure = "That's weird. There was a problem deleting the user.";

    private DataProvider provider;
    private UsersContract.View activity;
    private RecyclerView recyclerView;

    public UsersPresenter(UsersContract.View activity){
        this.activity = activity;
        this.provider = DataProviderFactory.getDataProviderInstance();
        provider.addObserver(this);
    }

    // public methods =====================================================================

    public void initializeRecyclerView(Context context, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.recyclerView.setHasFixedSize(true);

        // linear layout manager
        RecyclerView.LayoutManager manager = new LinearLayoutManager(context);
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
        intent.putExtra(AccountActivity.EXTRA_NEXT_LESSON, user.getNextClass());
        intent.putExtra(AccountActivity.EXTRA_LAST_LESSON, user.getLastClass());
        ((Activity)activity).startActivity(intent);
    }

    /**
     * Prompts for a confirmation to delete the selected user
     * @param user The user to delete
     */
    public void onDeletePressed(final User user){
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity)activity);
        builder.setTitle(delete_title);
        builder.setMessage(String.format(Locale.US, delete_message, user.getUserName()));
        builder.setNegativeButton(no_delete, null);
        builder.setPositiveButton(yes_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteUser(user);
            }
        });
        builder.create().show();
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
    public void onUserDeleted(boolean success){
        if (success) {
            Toast.makeText((Activity) activity, delete_success, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText((Activity) activity, delete_failure, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUserCreationAttempted(boolean success, String name){
        provider.fetchUsers();
    }

    /**
     * Unhooks self from the list of data observers and ends the activity.
     */
    @Override
    public void end() {
        activity.destroySelf();
    }

    @Override
    public void onUserNameChanged(boolean success, String name){
        provider.fetchUsers();
    }

    // private methods ====================================================================

    private void deleteUser(User user){
        DataProviderFactory.getDataProviderInstance().deleteUser(user);
    }
}
