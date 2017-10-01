package com.teamup.mihaylov.teamup.Events.CreateEvent;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventFragment extends Fragment {
    public static final int DATEPICKER_FRAGMENT = 1;
    public static final int TIMEPICKER_FRAGMENT = 2;

    private Button.OnClickListener mCreateEventBtnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            String eventName = mInputEventName.getText().toString().trim();
            String eventDescription = mInputEventDescription.getText().toString().trim();

            mProgressBar.setVisibility(View.VISIBLE);

            ((CreateEventActivity)getActivity()).add(eventName, eventDescription);
            Intent intent = new Intent(getActivity(), DrawerNavMainActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener mPickDateBtnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new EventDatePickerFragment();
            newFragment.setTargetFragment(CreateEventFragment.this, DATEPICKER_FRAGMENT);
            newFragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "date_picker");
        }
    };
    private Button.OnClickListener mPickTimeBtnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new EventTimePickerFragment();
            newFragment.setTargetFragment(CreateEventFragment.this, TIMEPICKER_FRAGMENT);
            newFragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "time_picker");
        }
    };


    private Button mBtnCreateEvent;
    private EditText mInputEventName;
    private EditText mInputEventDescription;
    private ProgressBar mProgressBar;
    private Button mBtnPickDate;
    private Button mBtnPickTime;
    private TextView mShowDateTextView;
    private TextView mShowTimeTextView;
    private String mDate;
    private String mTime;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_create_event, container, false);

        mInputEventName = (EditText) view.findViewById(R.id.input_event_name);
        mInputEventDescription = (EditText) view.findViewById(R.id.input_event_description);

        mBtnPickDate = (Button) view.findViewById(R.id.btn_pick_date);
        mBtnPickTime = (Button) view.findViewById(R.id.btn_pick_time);

        mBtnPickDate.setOnClickListener(mPickDateBtnListener);
        mBtnPickTime.setOnClickListener(mPickTimeBtnListener);

        mShowDateTextView = (TextView) view.findViewById(R.id.show_date);
        mShowTimeTextView = (TextView) view.findViewById(R.id.show_time);

        mBtnCreateEvent = (Button) view.findViewById(R.id.btn_create_event);
        mBtnCreateEvent.setOnClickListener(mCreateEventBtnListener);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DATEPICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    mDate = bundle.getString("selected_date", "error");
                    mShowDateTextView.setText(mDate);
                }
                break;
            case TIMEPICKER_FRAGMENT:
                if (resultCode == Activity.RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    mTime = bundle.getString("selected_time", "error");
                    mShowTimeTextView.setText(mTime);
                }
                break;
        }
    }

    @Override
    public void onResume() {
        mProgressBar.setVisibility(View.GONE);
        super.onResume();
    }
}
