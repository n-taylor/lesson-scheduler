package com.ntaylor.lessonscheduler.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.fragments.UpcomingFragment;
import com.ntaylor.lessonscheduler.presenters.UpcomingPresenter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.DataObserver;

import java.util.List;

public class UpcomingActivity extends AppCompatActivity implements UpcomingFragment.UpcomingFragmentListener, UpcomingPresenter.View {

    private UpcomingPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UpcomingPresenter(this);
        setContentView(R.layout.activity_upcoming);
    }

    /**
     * Retrieves the appropriate data for the list and hooks it up to an adapter.
     */
    @Override
    public void onListInitialization(ListView listView) {
        presenter.initializeListView(listView);
    }
}
