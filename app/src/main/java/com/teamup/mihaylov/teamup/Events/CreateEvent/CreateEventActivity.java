package com.teamup.mihaylov.teamup.Events.CreateEvent;

import android.os.Bundle;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;

import javax.inject.Inject;

public class CreateEventActivity extends DrawerNavMainActivity {

    private CreateEventFragment mCreateEventView;
    private String mTime;
    private String mDate;

    @Inject
    CreateEventContracts.Presenter mCreateEventPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mCreateEventView = CreateEventFragment.newInstance();

        mCreateEventView.setPresenter(mCreateEventPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mCreateEventView)
                .commit();
    }

    @Override
    protected void onResume() {
        mCreateEventView.setPresenter(mCreateEventPresenter);
        super.onResume();
    }
}
