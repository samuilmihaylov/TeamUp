package com.teamup.mihaylov.teamup.Events.ListEvents;

import android.os.Bundle;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;

import javax.inject.Inject;

public class ListEventsActivity extends DrawerNavMainActivity {

    @Inject
    ListEventsContracts.Presenter mListEventsPresenter;

    private ListEventsView mListEventsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListEventsView = ListEventsView.newInstance();

        mListEventsView.setPresenter(mListEventsPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mListEventsView)
                .commit();
    }
}
