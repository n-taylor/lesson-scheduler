package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.activities.AccountActivity;
import com.ntaylor.lessonscheduler.activities.ClassroomEditActivity;
import com.ntaylor.lessonscheduler.classes.ClassesAdapter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

public class ClassesPresenter implements DataObserver{

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
        adapter = new ClassesAdapter(((Activity)view).getApplicationContext(), classes, this);
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }

    public interface ClassesView {

    }
}
