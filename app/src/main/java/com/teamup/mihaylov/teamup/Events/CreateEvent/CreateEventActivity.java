package com.teamup.mihaylov.teamup.Events.CreateEvent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.R;

import javax.inject.Inject;

public class CreateEventActivity extends DrawerNavMainActivity {

    private CreateEventView mCreateEventView;

    @Inject
    CreateEventContracts.Presenter mCreateEventPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mCreateEventView = CreateEventView.newInstance();

        mCreateEventView.setPresenter(mCreateEventPresenter);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_container, mCreateEventView)
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onResume() {
        mCreateEventView.setPresenter(mCreateEventPresenter);
        super.onResume();
    }
}
