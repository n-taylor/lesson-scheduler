package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.activities.ClassesActivity;
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
        adapter = new ClassesAdapter(((Activity)view).getApplicationContext(), classes);
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }

    public interface ClassesView {

    }
}
