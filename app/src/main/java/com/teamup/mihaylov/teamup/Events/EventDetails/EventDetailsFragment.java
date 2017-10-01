package com.teamup.mihaylov.teamup.Events.EventDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {


    private TextView mEventName;
    private TextView mEventDescription;

    public EventDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_event_details, container, false);

        Event event = ((EventDetailsActivity)getActivity()).getEvent();

        mEventName = (TextView) view.findViewById(R.id.event_name);
        mEventDescription = (TextView) view.findViewById(R.id.event_description);

        mEventName.setText("Name: " + event.getName());
        mEventDescription.setText("Description: " + event.getDescription());

        return view;
    }
}