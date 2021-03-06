package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.interfaces.ClassroomEditContract;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public class ClassroomEditPresenter extends Presenter implements ClassroomEditContract.Presenter {

    private static final String NO_NAME_MESSAGE = "Please enter a name for the class";

    private ClassroomEditContract.View view;
    private DataProvider provider;

    private Classroom classroom;

    public ClassroomEditPresenter(ClassroomEditContract.View activity, Classroom classroom){
        this.view = activity;
        this.classroom = classroom;
        if (activity instanceof Activity){
            this.provider = DataProviderFactory.getDataProviderInstance();
//            this.provider.addObserver(this); // Not necessary at this time
        }

        // If the classroom exists already, display its current name
        if (classroom != null){
            view.setNameText(classroom.getClassName());
        }
    }

    /**
     * To be called when the delete button is pressed. Removes the classroom from the db.
     */
    public void onCancelPressed(Context context){
        view.destroySelf();
    }

    /**
     * To be called when the confirm button is pressed. Updates or adds the classroom to the db.
     * @param context The activity context
     */
    public void onConfirmClicked(Context context){
        String name = view.getNameText();
        if (name == null || name.isEmpty()){
            // The name is invalid, so show an error message
            Toast.makeText(context, NO_NAME_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        else if (classroom == null){
            // Create a new class with the given name and finish the activity
            provider.createClassroom(name, UserInfo.getUserInfo().getOrgId());
            view.destroySelf();
        }
        else {
            // Update the classroom and finish the activity
            classroom.setClassName(name);
            provider.updateClassroom(classroom);
            view.destroySelf();
        }
    }

    /**
     * Unhooks self from the list of data observers and ends the activity.
     */
    @Override
    public void end() {
        view.destroySelf();
    }
}
