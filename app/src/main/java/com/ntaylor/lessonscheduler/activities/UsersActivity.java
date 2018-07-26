package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.UsersPresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class UsersActivity extends AppCompatActivity implements UsersPresenter.UsersView {

    private UsersPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.presenter = new UsersPresenter(this);
        initializeButtons();
        initializeRecyclerView();
    }

    @Override
    public void destroySelf() {
        DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        finish();
    }

    // private methods =====================================================================

    private void initializeButtons(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddUserPressed();
            }
        });
    }

    private void initializeRecyclerView(){
        RecyclerView view = (RecyclerView)findViewById(R.id.users_recycler_view);
        presenter.initializeRecyclerView(this, view);
    }
}
