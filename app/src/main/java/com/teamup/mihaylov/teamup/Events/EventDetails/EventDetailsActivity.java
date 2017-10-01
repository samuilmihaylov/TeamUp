package com.teamup.mihaylov.teamup.Events.EventDetails;

import android.os.Bundle;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

public class EventDetailsActivity extends DrawerNavMainActivity {

    private EventDetailsFragment mEventDetailsFragment;
    private Event mEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEventDetailsFragment = new EventDetailsFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mEventDetailsFragment)
                .commit();

        mEvent = (Event) getIntent().getParcelableExtra("eventDetails");
    }

    public Event getEvent(){
        return mEvent;
    }
}
