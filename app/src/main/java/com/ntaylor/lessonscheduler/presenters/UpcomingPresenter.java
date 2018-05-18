package com.ntaylor.lessonscheduler.presenters;

import android.content.Context;
import android.text.style.TtsSpan;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;
import com.ntaylor.lessonscheduler.upcoming.UpcomingAdapter;
import com.ntaylor.lessonscheduler.util.SimpleDate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * This class acts as the Presenter for the UpcomingFragment in a MVP architecture.
 *
 * Created by Nathan Taylor on 5/14/2018.
 */

public class UpcomingPresenter {

    private View fragment;

    public UpcomingPresenter(View fragment){
        this.fragment = fragment;
    }

    /**
     * Retrieves the appropriate data for the list and hooks it up to an adapter.
     */
    public void initializeListView(Context context, ListView listView){
        ArrayList<SimpleDate> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 5, 27);
        SimpleDate date = new SimpleDate();
        dates.add(new SimpleDate());
        for (int i = 0; i < 15; i++){
            date = date.addOneWeek();
            dates.add(date);
        }
        UpcomingAdapter adapter = new UpcomingAdapter(context, dates);
        listView.setAdapter(adapter);
    }

    public interface View {

    }
}
