package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.UsersPresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class UsersActivity extends AppCompatActivity implements UsersPresenter.UsersView {

    private UsersPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        this.presenter = new UsersPresenter(this);
        initializeButtons();
        initializeRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_users, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_classes:
                // User chose the "Settings" item, show the app settings UI...
                presenter.onClassesItemPressed(this);
                return true;
            case R.id.action_account:
                presenter.onAccountItemPressed(this);
                return true;
            case R.id.action_upcoming:
                presenter.onUpcomingItemPressed(this);
                return true;
            case R.id.action_assignments:
                presenter.onAssignmentsItemPressed(this);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
