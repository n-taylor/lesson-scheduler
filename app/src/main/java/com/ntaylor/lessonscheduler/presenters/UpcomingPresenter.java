package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.room.entities.Assignment;
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
        DataProviderFactory.getDataProviderInstance().fetchAssignments();
    }

    @Override
    public void onAssignmentsUpdated(List<Assignment> assignments) {
        if(dataProvider != null) {
            UpcomingAdapter adapter = new UpcomingAdapter(((Activity) activity).getApplicationContext(), assignments);
            listView.setAdapter(adapter);
        }
    }

    public interface View {

    }
}
