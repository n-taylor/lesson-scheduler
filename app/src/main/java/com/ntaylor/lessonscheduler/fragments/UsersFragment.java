package com.ntaylor.lessonscheduler.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntaylor.lessonscheduler.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class UsersFragment extends Fragment {

    public UsersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }
}
