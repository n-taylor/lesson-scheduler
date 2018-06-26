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

    private ClassesFragmentListener listener;
    private ListView listView;

    public ClassesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classes, container, false);
    }


    @Override
    public void onStart(){
        initializeListView();
        super.onStart();
    }

    /**
     * Sets up the list view using the listener
     */
    private void initializeListView(){
        listView = (ListView) getActivity().findViewById(R.id.classes_list_view);
        listener.onListInitialization(listView);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ClassesFragmentListener) {
            listener = (ClassesFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ClassesFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface ClassesFragmentListener {
        void onListInitialization(ListView listView);
    }
}
