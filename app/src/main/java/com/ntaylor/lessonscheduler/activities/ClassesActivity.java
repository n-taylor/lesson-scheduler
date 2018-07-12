package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.fragments.ClassesFragment;
import com.ntaylor.lessonscheduler.presenters.ClassesPresenter;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

public class ClassesActivity extends AppCompatActivity implements ClassesPresenter.ClassesView {

    private ClassesPresenter presenter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ClassesPresenter(this);
        setContentView(R.layout.activity_classes);
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        initializeButtons();
        initializeListView();
    }

    @Override
    protected void onDestroy(){
        if (presenter != null){
            DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        }
        super.onDestroy();
    }

    private void initializeButtons(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddButtonPressed();
            }
        });
    }

    /**
     * Sets up the list view using the listener
     */
    private void initializeListView(){
        listView = (ListView)findViewById(R.id.classes_list_view);
        presenter.initializeListView(listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_classes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;
            case R.id.action_account:
                presenter.onAccountItemPressed(this);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Call finish()
     */
    @Override
    public void destroySelf() {
        DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        finish();
    }
}
