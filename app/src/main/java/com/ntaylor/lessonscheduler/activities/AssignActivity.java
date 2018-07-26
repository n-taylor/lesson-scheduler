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

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.AssignPresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class AssignActivity extends AppCompatActivity implements AssignPresenter.AssignView {

    public static final String EXTRA_CLASS_ID = "id";
    public static final String EXTRA_CLASS_NAME = "name";
    public static final String EXTRA_DATE = "date";

    private RecyclerView recyclerView;
    private AssignPresenter presenter;

    // Overrides =========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        this.presenter = createPresenter();
        presenter.initializeClassroomView(this);
        initializeRecyclerView();
    }

    @Override
    public void destroySelf() {
        DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_assign, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upcoming:
                // User chose the "Settings" item, show the app settings UI...
                presenter.onUpcomingItemPressed(this);
                return true;
            case R.id.action_account:
                presenter.onAccountItemPressed(this);
                return true;
            case R.id.action_classes:
                presenter.onClassesItemPressed(this);
                return true;
            case R.id.action_users:
                presenter.onUsersItemPressed(this);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    // private methods ===================================================================

    /**
     * Extracts the necessary values from the bundle to create an assign presenter
     * @return a presenter for this activity
     */
    private AssignPresenter createPresenter(){
        Bundle bundle = getIntent().getExtras();
        String id = (String)bundle.get(EXTRA_CLASS_ID);
        String name = (String)bundle.get(EXTRA_CLASS_NAME);
        String date = (String)bundle.get(EXTRA_DATE);

        return new AssignPresenter(this, id, name, date);
    }

    private void initializeRecyclerView(){
        this.recyclerView = (RecyclerView)findViewById(R.id.assign_users_view);
        presenter.initializeRecyclerView(this, recyclerView);
    }
}
