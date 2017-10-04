package com.teamup.mihaylov.teamup.Events.CreateEvent;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.BaseData;
import com.teamup.mihaylov.teamup.base.data.RemoteData;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by samui on 4.10.2017 г..
 */

public class CreateEventPresenter implements CreateEventContracts.Presenter {

    private final RemoteData<Event> mRemoteData;
    private final AuthenticationProvider mAuthProvider;
    private final String mCurrentUserName;
    private final String mCurrentUserId;
    private CreateEventContracts.View mView;

    @Inject
    public CreateEventPresenter(AuthenticationProvider authProvider, RemoteData<Event> remoteData) {
        mAuthProvider = authProvider;
        mRemoteData = remoteData;

        mCurrentUserName = mAuthProvider.getUser().getDisplayName();
        mCurrentUserId = mAuthProvider.getUser().getUid();
    }

    @Override
    public void subscribe(CreateEventContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void addEvent(String eventName, String eventDescription, String date, String time) {
        ArrayList<String> participants = new ArrayList<String>();
        participants.add(mAuthProvider.getUserId());

        String eventId = mRemoteData.getKey();

        Event event = new Event(eventId, eventName, eventDescription, date, time, mCurrentUserName, mCurrentUserId, participants);

        mRemoteData.add(event);
    }
}
