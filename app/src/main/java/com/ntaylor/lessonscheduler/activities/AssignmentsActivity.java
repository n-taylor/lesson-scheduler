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
import com.ntaylor.lessonscheduler.interfaces.AssignmentsContract;
import com.ntaylor.lessonscheduler.presenters.AssignmentsPresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class AssignmentsActivity extends AppCompatActivity implements AssignmentsContract.View {

    private AssignmentsContract.Presenter presenter;

    // Overrides =========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        presenter = new AssignmentsPresenter(this);

        initializeRecycler();
    }

    @Override
    public void destroySelf() {
        DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_assignments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upcoming:
                presenter.onUpcomingItemPressed(this);
                return true;
            case R.id.action_account:
                presenter.onAccountItemPressed(this);
                return true;
            case R.id.action_classes:
                presenter.onAccountItemPressed(this);
                return true;
            case R.id.action_users:
                presenter.onUsersItemPressed(this);
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

    // private methods ==================================================================

    private void initializeRecycler(){
        RecyclerView list = (RecyclerView)findViewById(R.id.assignments_recycler);
        presenter.initializeRecycler(this, list);
    }
}
