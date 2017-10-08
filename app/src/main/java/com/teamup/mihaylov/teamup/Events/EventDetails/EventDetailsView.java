package com.teamup.mihaylov.teamup.Events.EventDetails;


import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventDetailsView extends Fragment implements EventDetailsContracts.View, OnMapReadyCallback {

    private static final float DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private EventDetailsContracts.Presenter mPresenter;

    private GoogleMap mGoogleMap;

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

    private Location mLastKnownLocation;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MapView mapView;
    private Marker mCurrLocationMarker;
    private Event mEvent;

    public EventDetailsView() {
        // Required empty public constructor
    }

    public static EventDetailsView newInstance() {
        return new EventDetailsView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_details, container, false);

        mEvent = mPresenter.getEvent();

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        mEventName = (TextView) view.findViewById(R.id.event_name);
        mEventDescription = (TextView) view.findViewById(R.id.event_description);

        mEventName.setText("Name: " + mEvent.getName());
        mEventDescription.setText("Description: " + mEvent.getDescription());

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        updateLocationUI();
                }
            }
        }
    }

    private boolean checkAndRequestPermissions() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            return false;
        }

        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        updateLocationUI();

        getDeviceLocation();

        getMarkerLocation();
    }

    private void updateLocationUI() {
        if (mGoogleMap == null) {
            return;
        }
        try {
            if (checkAndRequestPermissions()) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                checkAndRequestPermissions();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        try {
            if (checkAndRequestPermissions()) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = (Location) task.getResult();
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d("GoogleMaps", "Current location is null. Using defaults.");
                            Log.e("GoogleMaps", "Exception: %s", task.getException());
                            LatLng defaultCoordinated = new LatLng(42.61779143282346, 25.400390625);
                            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(defaultCoordinated, 5));
                            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void getMarkerLocation()
    {
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        LatLng latLng = new LatLng(mEvent.getCoordinates().get(0), mEvent.getCoordinates().get(1));
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(mEvent.getName());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(30));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }
}
