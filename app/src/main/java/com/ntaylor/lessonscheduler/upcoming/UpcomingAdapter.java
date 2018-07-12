package com.ntaylor.lessonscheduler.upcoming;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.room.entities.Assignment;
import com.ntaylor.lessonscheduler.room.entities.Classroom;
import com.ntaylor.lessonscheduler.util.SimpleDate;

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

    public UpcomingAdapter(@NonNull Context context, List<SimpleDate> dates, List<Assignment> assignments, List<Classroom> classes) {
        super(context,0, dates);
        this.dates = dates;
        this.context = context;
        this.classes = classes;
        this.assignments = assignments;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

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
        subtext.setText(getUnassignedClassNames());

        return convertView;
    }

    private String getUnassignedClassNames(){
        StringBuilder sb = new StringBuilder();
        for (Classroom classroom : classes){
            if (!assignmentsContain(classroom)){
                if (!sb.toString().isEmpty()){
                    sb.append(", ");
                }
                sb.append(classroom.getClassName());
            }
        }

        return sb.substring(0, max_characters) + "...";
    }

    private boolean assignmentsContain(Classroom classroom){
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
    }
}
