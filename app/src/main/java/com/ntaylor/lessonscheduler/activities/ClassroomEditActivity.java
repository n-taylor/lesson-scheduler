package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.ClassroomEditPresenter;

public class ClassroomEditActivity extends AppCompatActivity implements ClassroomEditPresenter.ClassroomEditView {

    private ClassroomEditPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ClassroomEditPresenter(this);
        setContentView(R.layout.activity_classroom_edit);

    }

}
