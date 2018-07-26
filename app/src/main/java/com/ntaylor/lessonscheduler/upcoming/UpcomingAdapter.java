package com.ntaylor.lessonscheduler.upcoming;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.UpcomingPresenter;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The adapter for the upcoming weeks list to be displayed.
 *
 * Created by Nathan Taylor on 5/15/2018.
 */

public class UpcomingAdapter extends ArrayAdapter<SimpleDate> {

    private static final int max_characters = 38;

    private List<SimpleDate> dates;
    private Context context;

    private List<Classroom> classes;
    private List<Assignment> assignments;
    private HashMap<SimpleDate, List<Assignment>> assignmentsMap;

    private UpcomingPresenter presenter;

    public UpcomingAdapter(UpcomingPresenter presenter, @NonNull Context context, List<SimpleDate> dates, List<Assignment> assignments, List<Classroom> classes) {
        super(context,0, dates);
        this.presenter = presenter;
        this.dates = dates;
        this.context = context;
        this.classes = classes;
        this.assignments = assignments;

        initializeAssignmentsMap();
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.upcoming_item, parent, false);
        }

//        convertView.setBackgroundColor(Color.RED);
        TextView dateTextView = (TextView)convertView.findViewById(R.id.upcoming_title);
        TextView assignmentsLeft = (TextView)convertView.findViewById(R.id.upcoming_number_left);
        TextView subtext = (TextView)convertView.findViewById(R.id.upcoming_subtext);

        SimpleDate date = dates.get(position);

        dateTextView.setText(date.toString());
        assignmentsLeft.setText(String.valueOf(getNumUnassignedClasses(date)));
        subtext.setText(getUnassignedClassNames(date));

        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onDatePressed(context, dates.get(position).serializeDate());
            }
        });

        return convertView;
    }

    /**
     * Gets a list of the classes yet to be assigned a teacher for the particular date.
     * If the list is too long, shortens it and appends "..."
     * @param date The date on which the unassigned classes are relevant
     * @return Ex. "Class 1, Class 2, Class 3, Cl..."
     */
    private String getUnassignedClassNames(SimpleDate date){
        StringBuilder sb = new StringBuilder();
        ArrayList<String> unassigned = new ArrayList<>();

        // fill the unassigned list with all the classes' IDs
        for (Classroom classroom : classes){
            unassigned.add((classroom.getClassId()));
        }

        // remove any classes that have been assigned
        for (Assignment assignment : assignmentsMap.get(date)){
            unassigned.remove(assignment.getClassId());
        }

        // match class IDs to names and add to the string builder
        for (Classroom classroom : classes){
            if (unassigned.contains(classroom.getClassId())){
                if (sb.length() != 0){
                    sb.append(", ");
                }
                sb.append(classroom.getClassName());
            }
        }

        // shorten the full description if needed
        return (sb.length() > max_characters) ? sb.substring(0, max_characters) + "..." : sb.toString();
    }

    private boolean assignmentsContain(Classroom classroom, SimpleDate date){
        for (Assignment assignment : assignments){
            if (assignment.getClassId().equals(classroom.getClassId())){
                return true;
            }
        }
        return false;
    }

    private int getNumUnassignedClasses(SimpleDate date){
        int numClasses = (classes != null) ? classes.size() : 0;
        int numAssignment = 0;
        if (assignments != null) {
            for (Assignment assignment : assignments) {
                if (assignment.getDate().equals(date.serializeDate())) {
                    numAssignment++;
                }
            }
        }
        return numClasses - numAssignment;
    }

    /**
     * Updates the list of available classes
     */
    public void setClasses(List<Classroom> classes){
        this.classes = classes;
    }

    /**
     * Updates the list of upcoming assignments
     */
    public void setAssignments(List<Assignment> assignments) {
        this.assignments = assignments;

        initializeAssignmentsMap();
    }

    /**
     * Populates the assignments hashmap with all the current assignments for each date listed
     * in this adapter.
     */
    private void initializeAssignmentsMap(){

        this.assignmentsMap = new HashMap<>();
        for (SimpleDate date : dates){
            assignmentsMap.put(date, new ArrayList<Assignment>());
        }

        for (Assignment assignment : assignments){
            SimpleDate date = SimpleDate.deserializeDate(assignment.getDate(), true);
            if (assignmentsMap.keySet().contains(date)){
                assignmentsMap.get(date).add(assignment);
            }
        }

    }
}
