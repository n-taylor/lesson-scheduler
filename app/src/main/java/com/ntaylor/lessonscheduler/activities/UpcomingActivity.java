package com.ntaylor.lessonscheduler.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.fragments.UpcomingFragment;
import com.ntaylor.lessonscheduler.presenters.UpcomingPresenter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

import java.util.List;

public class UpcomingActivity extends AppCompatActivity implements UpcomingPresenter.View {

    private UpcomingPresenter presenter;

    private ListView listView;

    // Overrides ========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UpcomingPresenter(this);
        setContentView(R.layout.activity_upcoming);

        initializeListView();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        }
        super.onDestroy();
    }

    // Private Methods ===================================================================

    private void initializeListView(){
        listView = (ListView)findViewById(R.id.upcoming_list_view);
        presenter.initializeListView(listView);
    }
}
