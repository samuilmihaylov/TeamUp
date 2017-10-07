package com.teamup.mihaylov.teamup.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents.ListCreatedEventsActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents.ListJoinedEventsActivity;
import com.teamup.mihaylov.teamup.R;

public class GoogleProfileView extends Fragment implements UserProfileContracts.View{

    private Button btnSignOut;

    private UserProfileContracts.Presenter mPresenter;

    private Button.OnClickListener btnSignOutListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            mPresenter.googleSignOut();
            Intent intent = new Intent(getActivity(), DrawerNavMainActivity.class);
            startActivity(intent);
        }
    };

    private Spinner.OnItemSelectedListener spinnerSelectListener = new Spinner.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
            if (pos == 1) {
                Intent intent = new Intent(getContext(), ListJoinedEventsActivity.class);
                startActivity(intent);
            } else if (pos == 2) {
                Intent intent = new Intent(getContext(), ListCreatedEventsActivity.class);
                startActivity(intent);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    public GoogleProfileView() {
        // Required empty public constructor
    }

    public static GoogleProfileView newInstance() {
        return new GoogleProfileView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_google_profile, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.my_collections);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.my_collections_dropdown, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(spinnerSelectListener);

        btnSignOut = (Button) view.findViewById(R.id.sign_out);
        btnSignOut.setOnClickListener(btnSignOutListener);

        return view;
    }

    @Override
    public void setPresenter(UserProfileContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        mPresenter = null;
    }
}
