package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.AssignPresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class AssignActivity extends AppCompatActivity implements AssignPresenter.AssignView {

    private RecyclerView recyclerView;
    private AssignPresenter presenter;

    // Overrides =========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        this.presenter = new AssignPresenter(this);
        initializeRecyclerView();
    }

    @Override
    public void destroySelf() {
        DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        finish();
    }

    // private methods ===================================================================

    private void initializeRecyclerView(){
        this.recyclerView = (RecyclerView)findViewById(R.id.assign_users_view);
        presenter.initializeRecyclerView(this, recyclerView);
    }
}
