package com.ntaylor.lessonscheduler.classes;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.room.entities.Classroom;

import java.util.List;

public class ClassesAdapter extends ArrayAdapter<Classroom> {

    private List<Classroom> classrooms;
    private Context context;

    public ClassesAdapter(@NonNull Context context, List<Classroom> classesList){
        super(context, 0, classesList);

        this.classrooms = classesList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.upcoming_item, parent, false);
        }

        convertView.setBackgroundColor(Color.RED);
        TextView classNameTextView = (TextView)convertView.findViewById(R.id.class_name);
        classNameTextView.setText(classrooms.get(position).getClassName());

        return convertView;
    }
}
