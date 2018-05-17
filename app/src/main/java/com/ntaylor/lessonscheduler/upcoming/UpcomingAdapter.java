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
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.ArrayList;
import java.util.Date;

/**
 * The adapter for the upcoming weeks list to be displayed.
 *
 * Created by Nathan Taylor on 5/15/2018.
 */

public class UpcomingAdapter extends ArrayAdapter<SimpleDate> {

    private ArrayList<SimpleDate> dates;
    private Context context;

    public UpcomingAdapter(@NonNull Context context, ArrayList<SimpleDate> dates) {
        super(context,0, dates);
        this.dates = dates;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.upcoming_item, parent, false);
        }

        convertView.setBackgroundColor(Color.RED);
        TextView textView = (TextView)convertView.findViewById(R.id.upcoming_item_text);
        textView.setText(dates.get(position).toString());

        return convertView;
    }
}
