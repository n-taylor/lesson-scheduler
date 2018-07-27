package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.ListView;
import android.widget.Toast;

import com.ntaylor.lessonscheduler.activities.AccountActivity;
import com.ntaylor.lessonscheduler.activities.ClassroomEditActivity;
import com.ntaylor.lessonscheduler.classes.ClassesAdapter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;
import java.util.Locale;

public class ClassesPresenter extends Presenter {

    private static final String delete_class_title = "Delete Class";
    private static final String delete_class_message = "Are you sure you want to delete %s?";
    private static final String yes_delete = "Delete";
    private static final String no_delete = "Cancel";
    private static final String classroomDeleteMessage = "Class successfully deleted.";

    private ClassesView view;
    private ListView listView;
    private ClassesAdapter adapter;

    private DataProvider dataProvider;

    /**
     * Registers this presenter as a data observer.
     * @param activity Must also inherit from Activity
     */
    public ClassesPresenter(ClassesView activity){
        this.view = activity;
        if (activity instanceof Activity){
            this.dataProvider = DataProviderFactory.getDataProviderInstance();
            dataProvider.addObserver(this);
        }
    }

    public void initializeListView(ListView listView){
        this.listView = listView;
        if (adapter != null){
            listView.setAdapter(adapter);
        }
        dataProvider.fetchClasses();
    }

    /**
     * Displays account information for the current user.
     */
    public void onAccountItemPressed(Context context){
        Intent intent = new Intent(context, AccountActivity.class);
        context.startActivity(intent);
        view.destroySelf();
    }

    /**
     * To be called when the user presses a classroom in the list of classes.
     * Starts the ClassroomEditActivity with the given classroom.
     * @param classroom The classroom selected.
     */
    public void onClassroomPressed(Classroom classroom){
        if (view instanceof Activity){
            Activity activity = (Activity)view;
            Intent intent = new Intent(activity.getApplicationContext(), ClassroomEditActivity.class);
            intent.putExtra(ClassroomEditActivity.CLASS_ID_KEY, classroom.getClassId());
            intent.putExtra(ClassroomEditActivity.CLASS_NAME_KEY, classroom.getClassName());
            activity.startActivity(intent);
        }
    }

    /**
     * Deletes the classroom indicated.
     */
    public void onDeletePressed(final Classroom classroom){
        String message = String.format(Locale.US, delete_class_message, classroom.getClassName());
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity)view);
        builder.setTitle(delete_class_title)
                .setMessage(message)
                .setNegativeButton(no_delete, null)
                .setPositiveButton(yes_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteClass(classroom);
                    }
                })
                .create()
                .show();
    }

    /**
     * To be called when the user presses a button to add a classroom.
     * Starts the ClassroomEditActivity with the intention of adding a classroom.
     */
    public void onAddButtonPressed(){
        if (view instanceof Activity){
            Activity activity = (Activity)view;
            Intent intent = new Intent(activity.getApplicationContext(), ClassroomEditActivity.class);
            activity.startActivity(intent);
        }
    }

    /**
     * this will be called when the list of classes for the organization has been modified.
     *
     * @param classes The list of classes.
     */
    @Override
    public void onClassesUpdated(List<Classroom> classes) {
        adapter = new ClassesAdapter(((Activity)view).getApplicationContext(), classes, this);
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onClassroomDeleted(){
        Toast.makeText((Activity)view, classroomDeleteMessage, Toast.LENGTH_SHORT).show();
    }

    private void deleteClass(Classroom classroom){
        DataProviderFactory.getDataProviderInstance().deleteClassroom(classroom);
    }

    public interface ClassesView {
        /**
         * Call finish()
         */
        void destroySelf();
    }
}
