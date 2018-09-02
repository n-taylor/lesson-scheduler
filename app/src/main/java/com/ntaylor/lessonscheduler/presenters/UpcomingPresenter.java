package com.ntaylor.lessonscheduler.presenters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.activities.AssignActivity;
import com.ntaylor.lessonscheduler.activities.UpcomingActivity;
import com.ntaylor.lessonscheduler.interfaces.UpcomingContract;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.adapters.UpcomingAdapter;
import com.ntaylor.lessonscheduler.util.DataProvider;
import com.ntaylor.lessonscheduler.util.DataProviderFactory;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class acts as the Presenter for the UpcomingFragment in a MVP architecture.
 *
 * Created by Nathan Taylor on 5/14/2018.
 */

public class UpcomingPresenter extends Presenter implements UpcomingContract.Presenter {

    private static final String select_class_title = "Which class do you want to assign?";

    private UpcomingContract.View activity;
    private ListView listView;

    private DataProvider dataProvider;
    private UpcomingAdapter adapter;

    private List<Classroom> classes;
    private List<Assignment> assignments;
    private ArrayList<SimpleDate> dates;

    private static final int weeks_to_show = 5;

    public UpcomingPresenter(UpcomingContract.View activity){
        this.activity = activity;
        initializeDates();
        if (activity instanceof Activity){
            Activity act = (Activity)activity;
            dataProvider = DataProviderFactory.getDataProviderInstance();
            dataProvider.addObserver(this);
        }
    }

    // Public methods =======================================================================

    /**
     * Shows a dialog that allows the user to select the class to which they want to assign a teacher
     * @param date The date of the assignment to be made.
     * @param unassigned The names of all the unassigned classes.
     */
    public void onDatePressed(final Context context, final String date, final CharSequence[] unassigned){
        // If there are no unassigned classes, do nothing
        // If there is only 1 unassigned class, start the assign activity for that class
        // Otherwise, have the user select the course to assign
        if (unassigned.length == 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("No classes to assign");
            builder.setMessage("You're all done! Hooray!");
            builder.create().show();
        }
        else if (unassigned.length == 1){
            Classroom toAssign = getClassByName(unassigned[0].toString());
            if (toAssign != null) {
                startAssignActivity(context, toAssign.getClassId(), toAssign.getClassName(), date);
            }
        }
        else {
            // Create the dialog box
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(select_class_title);
            builder.setItems(unassigned, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int selected) {
                    // Open the assign activity with the appropriate class
                    Classroom chosen = getClassByName(unassigned[selected].toString());
                    startAssignActivity(context, chosen.getClassId(), chosen.getClassName(), date);
                }
            });
            builder.create().show();
        }
    }

    /**
     * Allows the list view to be filled with incoming data
     */
    public void initializeListView(ListView listView){
        this.listView = listView;
        DataProviderFactory.getDataProviderInstance().fetchAssignments();
        DataProviderFactory.getDataProviderInstance().fetchClasses();
    }

    // Overrides ============================================================================

    /**
     * Unhooks self from the list of data observers and ends the activity.
     */
    @Override
    public void end() {
        activity.destroySelf();
    }

    @Override
    public void onAssignmentsUpdated(List<Assignment> assignments) {
        this.assignments = assignments;
        if (assignments != null && this.classes != null) {
            initializeAdapter();
        }
    }

    /**
     * this will be called when the list of classes for the organization has been modified.
     *
     * @param classes The list of classes.
     */
    @Override
    public void onClassesUpdated(List<Classroom> classes) {
        this.classes = classes;
        if (assignments != null && this.classes != null) {
            initializeAdapter();
        }
    }

    // Private utility methods ==============================================================

    /**
     * Starts the assign activity.
     * @param id The ID of the class for which to make an assignment.
     */
    private void startAssignActivity(Context context, String id, String name, String date){
        Intent intent = new Intent(context, AssignActivity.class);
        intent.putExtra(AssignActivity.EXTRA_CLASS_ID, id);
        intent.putExtra(AssignActivity.EXTRA_CLASS_NAME, name);
        intent.putExtra(AssignActivity.EXTRA_DATE, date);
        context.startActivity(intent);
    }

    private void initializeDates(){
        SimpleDate sunday = new SimpleDate().upcomingSunday();
        dates = new ArrayList<>();
        for (int i = 0; i < weeks_to_show; i++){
            dates.add(sunday.addWeeks(i));
        }
    }

    private void initializeAdapter() {
        adapter = new UpcomingAdapter(this, ((UpcomingActivity) activity), dates, assignments, classes);
        listView.setAdapter(adapter);
    }

    private Classroom getClassByName(@NonNull String name){
        Classroom toReturn = null;
        for (Classroom classroom : classes){
            if (classroom.getClassName().equals(name)){
                return classroom;
            }
        }
        return null;
    }
}
