package com.teamup.mihaylov.teamup.UserProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;

public class GoogleProfileFragment extends Fragment {

    private Button btnSignOut;

    private Button.OnClickListener btnSignOutListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            ((UserProfileActivity) getActivity()).googleSignOut();
            Intent intent = new Intent(getActivity(), DrawerNavMainActivity.class);
            startActivity(intent);
        }
    };


    public GoogleProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_google_profile, container, false);

        btnSignOut = (Button) view.findViewById(R.id.sign_out);
        btnSignOut.setOnClickListener(btnSignOutListener);

        return view;
    }
}
