package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public class ClassroomEditPresenter extends Presenter {

    private static final String NO_NAME_MESSAGE = "Please enter a name for the class";
    private static final String CONFIRM_DELETE = "Are you sure you want to delete this class?";
    private static final String YES_DELETE = "Delete";
    private static final String NO_DELETE = "Cancel";

    private ClassroomEditView view;
    private DataProvider provider;

    private Classroom classroom;

    public ClassroomEditPresenter(ClassroomEditView activity, Classroom classroom){
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
    public void onDeleteClicked(Context context){

        // Show a dialog confirming that the user wants to delete the classroom
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setMessage(CONFIRM_DELETE);
        dialogBuilder.setNegativeButton(NO_DELETE, null);
        dialogBuilder.setPositiveButton(YES_DELETE, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteClassroom();
            }
        });
        dialogBuilder.create().show();
    }

    private void deleteClassroom(){
        provider.removeObserver(this);
        if (classroom == null){
            // Just finish the activity
            view.destroySelf();
        }
        else {
            // Delete the classroom and then exit the activity
            provider.deleteClassroom(classroom);
            view.destroySelf();
        }
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

    // Interface =====================================================================================

    public interface ClassroomEditView {
        /**
         * @return The current text of the classroom name EditText, or null if not initialized
         */
        String getNameText();

        /**
         * Sets the text of the classroom name EditText
         */
        void setNameText(String text);

        /**
         * @return The current text of the classroom description EditText, or null if not initialized
         */
        String getDescriptionText();

        /**
         * Sets the text of the classroom description EditText
         */
        void setDescriptionText(String text);

        /**
         * Finish the activity.
         */
        void destroySelf();
    }
}
