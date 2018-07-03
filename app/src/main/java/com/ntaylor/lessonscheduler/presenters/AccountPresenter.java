package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

public class AccountPresenter implements DataObserver {

    private AccountView view;
    private DataProvider provider;

    public AccountPresenter(AccountView view){
        this.view = view;
        if (view instanceof Activity) {
            this.provider = DataProviderFactory.getDataProviderInstance();
            provider.addObserver(this);
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

    public interface AccountView {

    }
}
