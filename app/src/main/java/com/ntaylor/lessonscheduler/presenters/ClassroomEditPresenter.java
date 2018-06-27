package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.tasks.classroom.CreateClassroomTask;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.UserInfo;

import java.util.List;

public class ClassroomEditPresenter implements DataObserver {

    private static final String NO_NAME_MESSAGE = "Please enter a name for the class";

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
            // Create a new class with the given name
            provider.createClassroom(name, UserInfo.getUserInfo().getOrgId());
        }
        else {
            // Update the classroom
            classroom.setClassName(name);
            provider.updateClassroom(classroom);
        }
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
