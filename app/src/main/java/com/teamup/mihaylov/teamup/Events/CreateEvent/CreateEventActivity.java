package com.teamup.mihaylov.teamup.Events.CreateEvent;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.Date;

public class CreateEventActivity extends DrawerNavMainActivity {

    private CreateEventFragment mCreateEventFragment;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private String mCurrentUserName;
    private String mCurrentUserId;
    private String mTime;
    private String mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mCreateEventFragment = new CreateEventFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mCreateEventFragment)
                .commit();

        mDatabase = FirebaseDatabase.getInstance().getReference("events");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserName = mAuth.getCurrentUser().getDisplayName();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
    }

    public void add(String eventName, String eventDescription){
        Event event = new Event(eventName, eventDescription, mTime, mDate, mCurrentUserName, mCurrentUserId);
        mDatabase.push().setValue(event);
    }

    public void setTime(String time) {
        mTime = time;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getTime(){
        return this.mTime;
    }

    public String getDate() {
        return this.mDate;
    }
}
