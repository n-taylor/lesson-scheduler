package com.ntaylor.lessonscheduler.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.ClassroomEditPresenter;
import com.ntaylor.lessonscheduler.room.entities.Classroom;

/**
 * Updates, deletes or adds a classroom to the organization's list of classrooms.
 * To update a classroom, add it to this activity's bundle under the key CLASSROOM_KEY.
 */
public class ClassroomEditActivity extends AppCompatActivity implements ClassroomEditPresenter.ClassroomEditView {

    public static final String CLASSROOM_KEY = "class_key";

    private ClassroomEditPresenter presenter;
    private Button deleteButton;
    private Button confirmButton;
    private EditText nameText;
    private EditText descriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeViews();
        initializePresenter();
        setContentView(R.layout.activity_classroom_edit);
        initializeButtons();
    }

    /**
     * Extracts the classroom from the bundle to instantiate the presenter with the
     * classroom to update (null if adding a classroom).
     */
    private void initializePresenter(){
        Bundle bundle = getIntent().getExtras();
        if (bundle == null || bundle.isEmpty() || !bundle.containsKey(CLASSROOM_KEY)) {
            presenter = new ClassroomEditPresenter(this, null);
        }
        else {
            Classroom room = (Classroom)bundle.get(CLASSROOM_KEY);
            presenter = new ClassroomEditPresenter(this, room);
        }
    }

    /**
     * Initializes view elements, apart from buttons.
     */
    private void initializeViews(){
        this.nameText = (EditText)findViewById(R.id.classroom_edit_name_text);
        this.descriptionText = (EditText)findViewById(R.id.classroom_edit_description_text);
    }

    /**
     * Creates the buttons and hooks up their handlers.
     */
    private void initializeButtons(){
        deleteButton = (Button)findViewById(R.id.classroom_edit_delete_button);
        confirmButton = (Button)findViewById(R.id.classroom_edit_confirm_button);

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onDeleteClicked(ClassroomEditActivity.this);
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onConfirmClicked(ClassroomEditActivity.this);
            }
        });
    }

    /**
     * @return The current text of the classroom name EditText, or null if not initialized
     */
    @Override
    public String getNameText() {
        if (nameText == null)
            return null;
        else
            return nameText.getText().toString();
    }

    /**
     * Sets the text of the classroom name EditText
     */
    @Override
    public void setNameText(String text) {
        if (nameText != null){
            nameText.setText(text);
        }
    }

    /**
     * @return The current text of the classroom description EditText, or null if not initialized
     */
    @Override
    public String getDescriptionText() {
        if (descriptionText == null)
            return null;
        else {
            return descriptionText.getText().toString();
        }
    }

    /**
     * Sets the text of the classroom description EditText
     */
    @Override
    public void setDescriptionText(String text) {
        if (descriptionText != null){
            descriptionText.setText(text);
        }
    }

    /**
     * Finish the activity.
     */
    @Override
    public void destroySelf() {
        this.finish();
    }
}
