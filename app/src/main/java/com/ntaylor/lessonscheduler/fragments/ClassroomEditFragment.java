package com.ntaylor.lessonscheduler.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ntaylor.lessonscheduler.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ClassroomEditFragment extends Fragment {

    private ClassroomEditFragmentListener listener;

    public ClassroomEditFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classroom_edit, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ClassroomEditFragment.ClassroomEditFragmentListener) {
            listener = (ClassroomEditFragment.ClassroomEditFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ClassroomEditFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface ClassroomEditFragmentListener{

    }
}
