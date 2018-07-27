package com.ntaylor.lessonscheduler.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.fragments.SignUpFragment;
import com.ntaylor.lessonscheduler.presenters.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpPresenter.View, SignUpFragment.SignUpListener {

    private SignUpPresenter presenter;

    private EditText userNameText;
    private EditText orgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViews();
        presenter = new SignUpPresenter(this);
        initializeButtons();

        // for ease of debugging quickly, automatically log in
//        presenter.onButtonPressed(this, "nathan", "test");
//        finish();
    }

    private void initializeViews(){
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userNameText = (EditText)findViewById(R.id.sign_up_user_name);
        orgText = (EditText)findViewById(R.id.sign_up_org);

        final TextView createLink = (TextView)findViewById(R.id.sign_up_org_link);
        createLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initializeButtons(){

        Button login = (Button)findViewById(R.id.log_in_button);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onButtonPressed(SignUpActivity.this, userNameText.getText().toString(),
                        orgText.getText().toString());
            }
        });

    }

    @Override
    public void destroySelf() {
        finish();
    }
}
