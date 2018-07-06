package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.upcoming.UpcomingAdapter;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

/**
 * This class acts as the Presenter for the UpcomingFragment in a MVP architecture.
 *
 * Created by Nathan Taylor on 5/14/2018.
 */

public class UpcomingPresenter implements DataObserver {

    private View activity;
    private ListView listView;

    private DataProvider dataProvider;
    private UpcomingAdapter adapter;

    private List<Classroom> classes;

    public UpcomingPresenter(View activity){
        this.activity = activity;
        if (activity instanceof Activity){
            Activity act = (Activity)activity;
            dataProvider = DataProviderFactory.getDataProviderInstance();
            dataProvider.addObserver(this);
        }
    }

    /**
     * Allows the list view to be filled with incoming data
     */
    public void initializeListView(ListView listView){
        this.listView = listView;
        if (adapter != null){
            listView.setAdapter(adapter);
        }
        DataProviderFactory.getDataProviderInstance().fetchAssignments();
    }

    @Override
    public void onAssignmentsUpdated(List<Assignment> assignments) {
        if(dataProvider != null) {
            adapter = new UpcomingAdapter(((Activity) activity).getApplicationContext(), assignments);
            if (listView != null) {
                listView.setAdapter(adapter);
            }
        }
    }

    /**
     * this will be called when the list of classes for the organization has been modified.
     *
     * @param classes The list of classes.
     */
    @Override
    public void onClassesUpdated(List<Classroom> classes) {
        this.classes = classes;
    }

    /**
     * To be called when an attempt to modify the user name has been fully attempted.
     *
     * @param successful true if the username was changed, false if not valid or already taken.
     * @param name
     */
    @Override
    public void onUserNameChanged(boolean successful, String name) {

    }

    public interface View {

    }
}
