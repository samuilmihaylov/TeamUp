package com.teamup.mihaylov.teamup.Events.EventDetails;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

public class EventDetailsActivity extends DrawerNavMainActivity {

    private EventDetailsFragment mEventDetailsFragment;
    private Event mEvent;
    private FirebaseUser mUser;
    private String mEventId;
    private DatabaseReference mDbEvensRef;
    private String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserId = mUser.getUid();

        mEventDetailsFragment = new EventDetailsFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mEventDetailsFragment)
                .commit();

        mEvent = (Event) getIntent().getParcelableExtra("event_details");
        mEventId = mEvent.getId();

        mDbEvensRef = FirebaseDatabase.getInstance().getReference("events");
    }

    public Event getEvent(){
        return mEvent;
    }

    public Boolean isAuthor() {
        return this.mEvent.getAuthorId().equals(mUserId);
    }

    public Boolean isParticipating(){
        return this.mEvent.getParticipants().contains(mUser.getUid());
    }

    public void joinEvent(){
        this.mEvent.addParticipant(mUser.getUid());
        mDbEvensRef.child(mEventId).setValue(this.mEvent);
    }

    public void leaveEvent(){
        this.mEvent.removeParticipant(mUser.getUid());
        mDbEvensRef.child(mEventId).setValue(mEvent);
    }
}
