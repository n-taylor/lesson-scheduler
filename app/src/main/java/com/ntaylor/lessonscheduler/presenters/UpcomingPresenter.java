package com.ntaylor.lessonscheduler.presenters;

import android.app.Fragment;
import android.content.Context;
import android.text.style.TtsSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.upcoming.UpcomingAdapter;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This class acts as the Presenter for the UpcomingFragment in a MVP architecture.
 *
 * Created by Nathan Taylor on 5/14/2018.
 */

public class UpcomingPresenter implements DataObserver {

    private View fragment;
    private ListView listView;

    private DataProvider dataProvider;

    public UpcomingPresenter(View fragment){
        this.fragment = fragment;
        if (fragment instanceof Fragment){
            Fragment frag = (Fragment)fragment;
            dataProvider = DataProviderFactory.getDataProviderInstance();
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
            UpcomingAdapter adapter = new UpcomingAdapter(((Fragment) fragment).getContext(), assignments);
            listView.setAdapter(adapter);
        }
    }

    public interface View {

    }
}
