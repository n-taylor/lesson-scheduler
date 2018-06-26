package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.fragments.ClassesFragment;
import com.ntaylor.lessonscheduler.presenters.ClassesPresenter;

public class ClassesActivity extends AppCompatActivity implements ClassesPresenter.ClassesView, ClassesFragment.ClassesFragmentListener {

    private ClassesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ClassesPresenter(this);
        setContentView(R.layout.activity_classes);

        initializeButtons();
    }

    private void initializeButtons(){
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onListInitialization(ListView listView) {
        presenter.initializeListView(listView);
    }
}
