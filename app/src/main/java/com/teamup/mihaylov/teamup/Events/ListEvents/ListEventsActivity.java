package com.teamup.mihaylov.teamup.Events.ListEvents;

import android.os.Bundle;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;

public class ListEventsActivity extends DrawerNavMainActivity {

    private ListEventsFragment mListEventsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListEventsFragment = new ListEventsFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mListEventsFragment)
                .commit();

    }
}
