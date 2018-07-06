package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.activities.ClassesActivity;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public class AccountPresenter implements DataObserver {

    private static final String EMPTY_NAME = "Please enter a non-empty name";
    private static final String SAME_NAME = "No changes found";
    private static final String NAME_CHANGE_FAIL = "The username entered is not valid or already taken. Please try another.";
    private static final String NAME_CHANGE_SUCCESS = "Username successfully changed to ";

    private AccountView view;
    private DataProvider provider;

    public AccountPresenter(AccountView view){
        this.view = view;
        if (view instanceof Activity) {
            this.provider = DataProviderFactory.getDataProviderInstance();
            provider.addObserver(this);
        }
        view.setUserText(UserInfo.getUserInfo().getUserName());
        view.setOrgLabel(UserInfo.getUserInfo().getOrgName());
    }

    /**
     * Checks if the username has been changed and, if it has been, determines its availability.
     * Changes the username if available.
     */
    public void onSaveButtonPressed(Context context){
        String name = view.getUserText();
        String current = UserInfo.getUserInfo().getUserName();
        if (!name.equals(current)){
            // the name field has been edited
            if (name.trim().isEmpty()){
                Toast.makeText(context, EMPTY_NAME, Toast.LENGTH_SHORT).show();
            }
            else {
                // change username here
                DataProviderFactory.getDataProviderInstance().attemptChangeUserName(context, name);
            }
        }
        else {
            Toast.makeText(context, SAME_NAME, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opens the classes activity
     */
    public void onClassesActionPressed(Context context){
        Intent intent = new Intent(context, ClassesActivity.class);
        context.startActivity(intent);
        view.destroySelf();
    }

    /**
     * Resets all modified editable fields
     */
    public void onCancelButtonPressed(Context context){
        view.setUserText(UserInfo.getUserInfo().getUserName());
    }

    /**
     * This will be called when the list of assignments has been modified.
     *
     * @param assignments The class-teacher assignments.
     */
    @Override
    public void onAssignmentsUpdated(List<Assignment> assignments) {

    }

    /**
     * this will be called when the list of classes for the organization has been modified.
     *
     * @param classes The list of classes.
     */
    @Override
    public void onClassesUpdated(List<Classroom> classes) {

    }

    /**
     * To be called when an attempt to modify the user name has been fully attempted.
     *
     * @param successful true if the username was changed, false if not valid or already taken.
     * @param name The requested new username
     */
    @Override
    public void onUserNameChanged(boolean successful, String name) {
        Context context = ((Activity)view).getApplicationContext();
        if (context == null){
            return;
        }
        if (successful){
            Toast.makeText(context, NAME_CHANGE_SUCCESS + name, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, NAME_CHANGE_FAIL, Toast.LENGTH_SHORT).show();
        }
    }

    public interface AccountView {

        void setUserText(String text);
        String getUserText();

        void setOrgLabel(String text);

        void destroySelf();
    }
}
