package com.teamup.mihaylov.teamup.Events.EventDetails;

import android.os.Bundle;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

import javax.inject.Inject;

public class EventDetailsActivity extends DrawerNavMainActivity {

    private Event mEvent;

    @Inject
    EventDetailsContracts.Presenter mEventDetailsPresenter;

    private EventDetailsView mEventDetailsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEventDetailsView = EventDetailsView.newInstance();

        mEvent = (Event) getIntent().getParcelableExtra("event_details");
        mEventDetailsPresenter.setEvent(mEvent);
        mEventDetailsPresenter.setEventId(mEvent.getId());

        mEventDetailsView.setPresenter(mEventDetailsPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mEventDetailsView)
                .commit();
    }

    @Override
    protected void onResume() {
        mEventDetailsView.setPresenter(mEventDetailsPresenter);
        super.onResume();
    }
}
