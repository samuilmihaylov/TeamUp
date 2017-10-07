package com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;

import javax.inject.Inject;

public class ListJoinedEventsActivity extends DrawerNavMainActivity {

    @Inject
    ListJoinedEventsContracts.Presenter mListJoinedEventsPresenter;

    private ListJoinedEventsView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mView = ListJoinedEventsView.newInstance();

        mView.setPresenter(mListJoinedEventsPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mView)
                .commit();
    }
}
