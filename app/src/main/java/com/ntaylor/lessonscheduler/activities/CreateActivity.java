package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.CreatePresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

/**
 * Allows the user to join and/or create an organization in the app.
 * To enable creating an organization, the boolean value true must be bundled with the key CREATE_ORG
 */
public class CreateActivity extends AppCompatActivity implements CreatePresenter.CreateView {

    public static final String CREATE_ORG = "create";

    private CreatePresenter presenter;
    private EditText orgName;
    private EditText orgCode;
    private Button joinCreate;
    private Button checkAvailable;

    // Overrides =========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        this.presenter = makePresenter();
        initializeButtons();
    }

    @Override
    public void destroySelf() {
        DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        finish();
    }

    /**
     * Sets the orgCode field to be invisible
     */
    @Override
    public void hideCodeField() {
        if (orgCode != null){
            orgCode.setVisibility(View.INVISIBLE);
        }
    }

    // private methods ==================================================================

    private CreatePresenter makePresenter(){
        boolean create = false;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(CREATE_ORG) && bundle.getBoolean(CREATE_ORG)){
            create = true;
        }
        return new CreatePresenter(this, create);
    }

    private void initializeViews(){
        this.orgName = (EditText)findViewById(R.id.create_org_name);
        this.orgCode = (EditText)findViewById(R.id.create_org_code);
    }

    private void initializeButtons(){
        this.checkAvailable = (Button)findViewById(R.id.create_check_available);
        this.joinCreate = (Button)findViewById(R.id.create_join_org_button);

        checkAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onCheckAvailablePressed(orgName.getText().toString());
            }
        });

        joinCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onJoinCreatePressed(orgName.getText().toString(), orgCode.getText().toString());
            }
        });
    }
}
