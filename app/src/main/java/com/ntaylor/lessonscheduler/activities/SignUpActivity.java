package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.fragments.SignUpActivityFragment;
import com.ntaylor.lessonscheduler.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpPresenter.View, SignUpActivityFragment.SignUpListener {

    private SignUpPresenter presenter;

    private EditText userNameText;
    private EditText orgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViews();
        presenter = new SignUpPresenter(this);
        initializeButtons();
    }

    private void initializeViews(){
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userNameText = (EditText)findViewById(R.id.sign_up_user_name);
        orgText = (EditText)findViewById(R.id.sign_up_org);
    }

    private void initializeButtons(){

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onButtonPressed(SignUpActivity.this, userNameText.getText().toString(),
                        orgText.getText().toString());
            }
        });

    }

}
