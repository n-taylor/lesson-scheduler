package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.AccountPresenter;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;

/**
 * Displays, creates or edits a user's account information
 */
public class AccountActivity extends AppCompatActivity implements AccountPresenter.AccountView{

    public static final String EXTRA_USER_NAME = "name";
    public static final String EXTRA_USER_ID = "id";

    private AccountPresenter presenter;
    private EditText userNameText;
    private TextView orgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        initializeViews();
        this.presenter = createPresenter();
        initializeButtons();
    }

    @Override
    protected void onDestroy(){
        if (presenter != null){
            DataProviderFactory.getDataProviderInstance().removeObserver(presenter);
        }
        super.onDestroy();
    }

    private AccountPresenter createPresenter(){
        Bundle bundle = getIntent().getExtras();
        String name = (bundle.containsKey(EXTRA_USER_NAME)) ? (String)bundle.get(EXTRA_USER_NAME) : null;
        String id = (bundle.containsKey(EXTRA_USER_ID)) ? (String)bundle.get(EXTRA_USER_ID) : null;
        return new AccountPresenter(this, id, name);
    }

    /**
     * Initializes text views except buttons
     */
    private void initializeViews(){
        userNameText = (EditText)findViewById(R.id.account_user_name);
        orgText = (TextView)findViewById(R.id.account_org);
    }

    /**
     * Initializes all buttons
     */
    private void initializeButtons(){
        Button saveButton = (Button) findViewById(R.id.account_save_button);
        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onSaveButtonPressed(AccountActivity.this);
            }
        });

        Button cancelButton = (Button) findViewById(R.id.account_cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onCancelButtonPressed(AccountActivity.this);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_upcoming:
                presenter.onUpcomingItemPressed(this);
                return true;
            case R.id.action_classes:
                presenter.onClassesActionPressed(AccountActivity.this);
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

    @Override
    public void setUserText(String text) {
        if (userNameText != null)
            userNameText.setText(text);
    }

    @Override
    public String getUserText() {
        if (userNameText == null)
            return null;
        else
            return userNameText.getText().toString();
    }

    @Override
    public void setOrgLabel(String text) {
        if (orgText != null)
            orgText.setText(text);
    }

    @Override
    public void destroySelf() {
        finish();
    }
}
