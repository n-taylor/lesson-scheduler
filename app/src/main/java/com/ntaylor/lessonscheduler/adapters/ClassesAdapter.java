package com.ntaylor.lessonscheduler.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.presenters.ClassesPresenter;
import com.ntaylor.lessonscheduler.room.entities.Classroom;

import java.util.List;

public class ClassesAdapter extends ArrayAdapter<Classroom> {

    private List<Classroom> classrooms;
    private Context context;

    private ClassesPresenter presenter;

    public ClassesAdapter(@NonNull Context context, List<Classroom> classesList, ClassesPresenter presenter){
        super(context, 0, classesList);

        this.classrooms = classesList;
        this.context = context;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.class_item_layout, parent, false);
        }

        final int index = position;
//        convertView.setBackgroundColor(Color.RED);
        TextView classNameTextView = (TextView)convertView.findViewById(R.id.class_name);
        classNameTextView.setText(classrooms.get(position).getClassName());

        TextView image = (TextView)convertView.findViewById(R.id.upcoming_number_left);
        image.setText(classrooms.get(position).getClassName().substring(0,1));

        ImageButton delete = (ImageButton)convertView.findViewById(R.id.class_delete_button);
        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onDeletePressed(classrooms.get(index));
            }
        });

        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                presenter.onClassroomPressed(classrooms.get(index));
            }
        });

        return convertView;
    }
}
