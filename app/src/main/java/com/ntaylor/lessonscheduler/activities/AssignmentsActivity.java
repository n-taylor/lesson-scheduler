package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.AssignmentsPresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class AssignmentsActivity extends AppCompatActivity implements AssignmentsPresenter.AssignmentsView {

    private AssignmentsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void destroySelf() {
        DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        finish();
    }
}
