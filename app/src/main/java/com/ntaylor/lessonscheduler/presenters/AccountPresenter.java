package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.activities.ClassesActivity;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.Locale;

public class AccountPresenter extends Presenter implements DataObserver {

    private static final String EMPTY_NAME = "Please enter a non-empty name";
    private static final String SAME_NAME = "No changes found";
    private static final String NAME_CHANGE_FAIL = "The username entered is not valid or already taken. Please try another.";
    private static final String NAME_CHANGE_SUCCESS = "Username successfully changed to ";
    private static final String USER_CREATED_SUCCESS = "%s was successfully added as a user";

    private AccountView view;
    private DataProvider provider;

    private String userId;
    private String userName;

    private boolean createUser = false;

    public AccountPresenter(AccountView view, String userID, String userName){
        this.view = view;
        if (view instanceof Activity) {
            this.provider = DataProviderFactory.getDataProviderInstance();
            provider.addObserver(this);
        }

        this.userId = userID;
        this.userName = userName;

        if (userId == null){
            createUser = true;
        }

        view.setUserText(userName);
        view.setOrgLabel(UserInfo.getUserInfo().getOrgName());
    }

    /**
     * Checks if the username has been changed and, if it has been, determines its availability.
     * Changes the username if available.
     */
    public void onSaveButtonPressed(Context context){
        String name = view.getUserText();
        String current = UserInfo.getUserInfo().getUserName();
        if (!name.equals(userName)){
            // the name field has been edited
            if (name.trim().isEmpty()){
                Toast.makeText(context, EMPTY_NAME, Toast.LENGTH_SHORT).show();
            }
            else {
                // change username or create user here
                if (createUser){
                    DataProviderFactory.getDataProviderInstance().attemptCreateUser(name, UserInfo.getUserInfo().getOrgId());
                }
                else {
                    DataProviderFactory.getDataProviderInstance().attemptChangeUserName(context, userId, name);
                }
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

    @Override
    public void onUserCreationAttempted(boolean success, String name){
        if (success){
            String message = String.format(Locale.US, USER_CREATED_SUCCESS, name);
            Toast.makeText((Activity)view, message, Toast.LENGTH_SHORT).show();
            view.destroySelf();
        }
        else {
            Toast.makeText((Activity)view, NAME_CHANGE_FAIL, Toast.LENGTH_SHORT).show();
        }
    }

    public interface AccountView {

        void setUserText(String text);
        String getUserText();

        void setOrgLabel(String text);

        void destroySelf();
    }
}
