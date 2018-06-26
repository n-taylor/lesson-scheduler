package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

public class ClassroomEditPresenter implements DataObserver {

    private ClassroomEditView view;
    private DataProvider provider;

    public ClassroomEditPresenter(ClassroomEditView activity){
        this.view = activity;
        if (activity instanceof Activity){
            this.provider = DataProviderFactory.getDataProviderInstance();
            this.provider.addObserver(this);
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

    public interface ClassroomEditView {

    }
}
