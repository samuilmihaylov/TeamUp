package com.teamup.mihaylov.teamup.Events.EventDetails;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsFragment extends Fragment {


    private TextView mEventName;
    private TextView mEventDescription;

    private Button mBtnJoinEvent;
    private Button mBtnLeaveEvent;
    private Button mBtnEditEvent;

    private Button.OnClickListener mBtnJoinEventListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((EventDetailsActivity) getActivity()).joinEvent();
            mBtnJoinEvent.setVisibility(View.GONE);
            mBtnLeaveEvent.setVisibility(View.VISIBLE);
        }
    };

    private Button.OnClickListener mBtnEditEventListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {

        }
    };

    private Button.OnClickListener mBtnLeaveEventListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((EventDetailsActivity) getActivity()).leaveEvent();
            mBtnJoinEvent.setVisibility(View.VISIBLE);
            mBtnLeaveEvent.setVisibility(View.GONE);
        }
    };

    public EventDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        Event event = ((EventDetailsActivity) getActivity()).getEvent();

        mEventName = (TextView) view.findViewById(R.id.event_name);
        mEventDescription = (TextView) view.findViewById(R.id.event_description);

        mEventName.setText("Name: " + event.getName());
        mEventDescription.setText("Description: " + event.getDescription());

        mBtnJoinEvent = (Button) view.findViewById(R.id.btn_join_event);
        mBtnEditEvent = (Button) view.findViewById(R.id.btn_edit_event);
        mBtnLeaveEvent = (Button) view.findViewById(R.id.btn_leave_event);

        mBtnJoinEvent.setOnClickListener(mBtnJoinEventListener);
        mBtnEditEvent.setOnClickListener(mBtnEditEventListener);
        mBtnLeaveEvent.setOnClickListener(mBtnLeaveEventListener);

        mBtnJoinEvent.setVisibility(View.GONE);
        mBtnLeaveEvent.setVisibility(View.GONE);
        mBtnEditEvent.setVisibility(View.GONE);

        if (((EventDetailsActivity) getActivity()).isAuthor()) {
            mBtnEditEvent.setVisibility(View.VISIBLE);
        } else if (((EventDetailsActivity) getActivity()).isParticipating()) {
            mBtnLeaveEvent.setVisibility(View.VISIBLE);
        } else {
            mBtnJoinEvent.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
