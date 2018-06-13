package com.ntaylor.lessonscheduler.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ntaylor.lessonscheduler.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpcomingFragmentListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class UpcomingFragment extends Fragment {

    private UpcomingFragmentListener listener;

    private ListView listView;

    public UpcomingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initializeListView();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false);
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
        listView = (ListView) getActivity().findViewById(R.id.upcoming_list_view);
        listener.onListInitialization(listView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof UpcomingFragmentListener) {
            listener = (UpcomingFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement UpcomingFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface UpcomingFragmentListener {
        void onListInitialization(ListView listView);
    }
}
