package com.teamup.mihaylov.teamup.Home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamup.mihaylov.teamup.Events.ListEvents.ListEventsActivity;
import com.teamup.mihaylov.teamup.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TextView mWelcomeEventsListLink;

    private View.OnClickListener mWelcomeEventsListLinkListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), ListEventsActivity.class);
            startActivity(intent);
        }
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        mWelcomeEventsListLink = (TextView) view.findViewById(R.id.home_text);
        mWelcomeEventsListLink.setOnClickListener(mWelcomeEventsListLinkListener);

        return view;
    }
}
