package com.ntaylor.lessonscheduler.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClassesFragment extends Fragment {

    public ClassesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classes, container, false);
    }
}
