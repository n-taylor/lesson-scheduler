package com.ntaylor.lessonscheduler.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.UpcomingPresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class UpcomingActivity extends AppCompatActivity implements UpcomingPresenter.View {

    private UpcomingPresenter presenter;

    private ListView listView;

    // Overrides ========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new UpcomingPresenter(this);
        setContentView(R.layout.activity_upcoming);
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        initializeListView();
    }

    @Override
    protected void onDestroy() {
        if (presenter != null) {
            DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        }
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_upcoming, menu);
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

    // Private Methods ===================================================================

    private void initializeListView(){
        listView = (ListView)findViewById(R.id.upcoming_list_view);
        presenter.initializeListView(listView);
    }
}
