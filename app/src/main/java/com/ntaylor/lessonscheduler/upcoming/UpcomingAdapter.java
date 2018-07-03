package com.ntaylor.lessonscheduler.upcoming;

import android.content.Context;
import android.graphics.Color;
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

public class UpcomingAdapter extends ArrayAdapter<Assignment> {

    private List<Assignment> assignments;
    private Context context;

    private List<Classroom> classes;

    public UpcomingAdapter(@NonNull Context context, List<Assignment> assignments) {
        super(context,0, assignments);
        this.assignments = assignments;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.upcoming_item, parent, false);
        }

//        convertView.setBackgroundColor(Color.RED);
        TextView textView = (TextView)convertView.findViewById(R.id.upcoming_title);
        textView.setText(assignments.get(position).getDate());

        return convertView;
    }

    private int getNumUnassignedClasses(SimpleDate date){

        return 0;
    }

    public void setClasses(List<Classroom> classes){
        this.classes = classes;
    }
}
