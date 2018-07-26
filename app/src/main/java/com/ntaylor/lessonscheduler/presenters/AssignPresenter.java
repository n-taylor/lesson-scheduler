package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.activities.AssignActivity;
import com.ntaylor.lessonscheduler.assign.AssignAdapter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.room.entities.User;
import com.ntaylor.lessonscheduler.util.DataObserver;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.List;
import java.util.Locale;

public class AssignPresenter extends Presenter {

    private static final String confirm_assign = "Assign %1$s to teach %2$s?";
    private static final String confirm_yes = "Yes";
    private static final String confirm_no = "No";

    private AssignView view;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager manager;
    private DataProvider provider;

    private String classId;
    private String name;
    private String date;

    public AssignPresenter(AssignView activity, String classId, String name, String date){
        this.view = activity;
        this.classId = classId;
        this.name = name;
        this.date = date;

        this.provider = DataProviderFactory.getDataProviderInstance();
        provider.addObserver(this);
    }

    // public methods ===========================================================================

    /**
     * Sets up the view that displays which classroom is being assigned
     * @param activity
     */
    public void initializeClassroomView(Activity activity){
        TextView header = (TextView)activity.findViewById(R.id.class_name);
        header.setText(this.name);
        ((TextView)activity.findViewById(R.id.class_description)).setText(SimpleDate.deserializeDate(this.date, true).toString());
    }

    public void initializeRecyclerView(Context context, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.recyclerView.setHasFixedSize(true);

        // linear layout manager
        manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);

        // fetch users
        provider.fetchUsers();
    }

    /**
     * Confirms that the user wants to assign the user to teach the class.
     *
     * @param teacher The selected user to assign to teach.
     */
    public void onTeacherPressed(final User teacher){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder((AssignActivity)view);
        dialogBuilder.setMessage(String.format(Locale.US, confirm_assign, teacher.getUserName(), this.name));
        dialogBuilder.setNegativeButton(confirm_no, null);
        dialogBuilder.setPositiveButton(confirm_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                makeAssignment(teacher);
            }
        });
        dialogBuilder.create().show();
    }

    // Overrides ================================================================================

    /**
     * To be called when the list of users in an organization has been updated.
     * Initialize the recyclerView adapter
     * @param users The current list of teachers in the user's organization.
     */
    @Override
    public void onUsersUpdated(List<User> users) {
        AssignAdapter adapter = new AssignAdapter(this, users);
        recyclerView.setAdapter(adapter);
    }

    // private methods ==========================================================================

    /**
     * Assigns the given teacher to teach the selected class.
     *
     * @param teacher The user to assign to teach.
     */
    private void makeAssignment(User teacher){
        provider.createAssignment(teacher.getUserId(), this.classId, SimpleDate.deserializeDate(this.date, true));
        view.destroySelf();
    }

    public interface AssignView {
        void destroySelf();
    }
}
