package com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents;

import android.os.Bundle;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;

import javax.inject.Inject;

public class ListCreatedEventsActivity extends DrawerNavMainActivity {

    @Inject
    ListCreatedEventsContracts.Presenter mListCreatedEventsPresenter;

    private ListCreatedEventsView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = ListCreatedEventsView.newInstance();

        mView.setPresenter(mListCreatedEventsPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mView)
                .commit();
    }
}
