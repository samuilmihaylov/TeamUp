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
public class EventDetailsView extends Fragment implements EventDetailsContracts.View{

    private EventDetailsContracts.Presenter mPresenter;

    private TextView mEventName;
    private TextView mEventDescription;

    private Button mBtnJoinEvent;
    private Button mBtnLeaveEvent;
    private Button mBtnEditEvent;

    private Button.OnClickListener mBtnJoinEventListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.joinEvent();
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
            mPresenter.leaveEvent();
            mBtnJoinEvent.setVisibility(View.VISIBLE);
            mBtnLeaveEvent.setVisibility(View.GONE);
        }
    };

    public EventDetailsView() {
        // Required empty public constructor
    }

    public static EventDetailsView newInstance() {
        return new EventDetailsView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        Event event = mPresenter.getEvent();

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

        if (mPresenter.isAuthor()) {
            mBtnEditEvent.setVisibility(View.VISIBLE);
        } else if (mPresenter.isParticipating()) {
            mBtnLeaveEvent.setVisibility(View.VISIBLE);
        } else {
            mBtnJoinEvent.setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void setPresenter(EventDetailsContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        mPresenter.subscribe(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mPresenter.unsubscribe();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        mPresenter = null;
    }
}
