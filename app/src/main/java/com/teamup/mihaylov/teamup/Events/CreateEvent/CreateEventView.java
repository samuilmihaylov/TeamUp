package com.teamup.mihaylov.teamup.Events.CreateEvent;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListEventsActivity;
import com.teamup.mihaylov.teamup.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateEventView extends Fragment implements CreateEventContracts.View {
    private final int DATEPICKER_FRAGMENT = 1;
    private final int TIMEPICKER_FRAGMENT = 2;
    private static final int PLACE_PICKER_REQUEST = 3;
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 4;

    private EditText mInputEventName;
    private EditText mInputEventDescription;
    private ProgressBar mProgressBar;

    private Button mBtnPickDate;
    private Button mBtnPickTime;
    private Button mBtnCreateEvent;

    private TextView mShowDateTextView;
    private TextView mShowTimeTextView;
    private TextView mShowLocationTextView;

    private String mDate;
    private String mTime;
    private String mLocation;
    private ArrayList<Double> mCoordinates;

    private Button.OnClickListener mCreateEventBtnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            String eventName = mInputEventName.getText().toString().trim();
            String eventDescription = mInputEventDescription.getText().toString().trim();

            mProgressBar.setVisibility(View.VISIBLE);

            mPresenter.addEvent(eventName, eventDescription, mDate, mTime, mLocation, mCoordinates);

            Intent intent = new Intent(getActivity(), ListEventsActivity.class);
            startActivity(intent);
        }
    };

    private Button.OnClickListener mPickDateBtnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new EventDatePickerFragment();
            newFragment.setTargetFragment(CreateEventView.this, DATEPICKER_FRAGMENT);
            newFragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "date_picker");
        }
    };

    private Button.OnClickListener mPickTimeBtnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogFragment newFragment = new EventTimePickerFragment();
            newFragment.setTargetFragment(CreateEventView.this, TIMEPICKER_FRAGMENT);
            newFragment.show(getActivity().getSupportFragmentManager().beginTransaction(), "time_picker");
        }
    };

    private Button.OnClickListener mBtnPickLocationListener = new Button.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (checkAndRequestPermissions()) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                Intent intent;
                try {
                    intent = builder.build(getActivity());
                    getActivity().startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    Log.v("testLocation", e.toString());
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    Log.v("testLocation", e.toString());
                }
            }
        }
    };

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_ID_MULTIPLE_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private CreateEventContracts.Presenter mPresenter;
    private TextView mBtnPickLocation;

    public CreateEventView() {
        // Required empty public constructor
    }

    public static CreateEventView newInstance() {
        return new CreateEventView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_event, container, false);

        mInputEventName = (EditText) view.findViewById(R.id.input_event_name);
        mInputEventDescription = (EditText) view.findViewById(R.id.input_event_description);

        mBtnPickDate = (Button) view.findViewById(R.id.btn_pick_date);
        mBtnPickTime = (Button) view.findViewById(R.id.btn_pick_time);

        mBtnPickDate.setOnClickListener(mPickDateBtnListener);
        mBtnPickTime.setOnClickListener(mPickTimeBtnListener);

        mShowDateTextView = (TextView) view.findViewById(R.id.show_date);
        mShowTimeTextView = (TextView) view.findViewById(R.id.show_time);
        mShowLocationTextView = (TextView) view.findViewById(R.id.show_location);

        mBtnCreateEvent = (Button) view.findViewById(R.id.btn_create_event);
        mBtnCreateEvent.setOnClickListener(mCreateEventBtnListener);

        mBtnPickLocation = (Button) view.findViewById(R.id.btn_pick_location);
        mBtnPickLocation.setOnClickListener(mBtnPickLocationListener);

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
            case PLACE_PICKER_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Place place = PlacePicker.getPlace(getContext(), data);
                    mShowLocationTextView.setText(place.getAddress());
                    mLocation = place.getAddress().toString();

                    LatLng latlangObj = place.getLatLng();
                    mCoordinates = new ArrayList<Double>();
                    mCoordinates.add(latlangObj.latitude);
                    mCoordinates.add(latlangObj.longitude);
                }
                break;
        }
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

    @Override
    public void setPresenter(CreateEventContracts.Presenter presenter) {
        mPresenter = presenter;
    }
}
