package com.teamup.mihaylov.teamup.Events.CreateEvent;

import android.util.Log;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samui on 4.10.2017 г..
 */

public class CreateEventPresenter implements CreateEventContracts.Presenter {

    private final RemoteEventsData<Event> mRemoteEventsData;
    private final AuthenticationProvider mAuthProvider;
    private final String mCurrentUserName;
    private final String mCurrentUserId;
    private final RemoteUsersData<User> mRemoteUsersData;

    private CreateEventContracts.View mView;

    @Inject
    public CreateEventPresenter(AuthenticationProvider authProvider,
                                RemoteEventsData<Event> remoteEventsData,
                                RemoteUsersData<User> remoteUsersData) {
        mAuthProvider = authProvider;
        mRemoteEventsData = remoteEventsData;
        mRemoteUsersData = remoteUsersData;

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
    public void addEvent(final String eventName, final String eventDescription, final String date, final String time) {

        final String eventId = mRemoteEventsData.getKey();
        ArrayList<String> participants = new ArrayList<>();
        participants.add(mCurrentUserId);

        Event event = new Event(
                eventId,
                eventName,
                eventDescription,
                date,
                time,
                mCurrentUserName,
                mCurrentUserId,
                participants);

        mRemoteEventsData.add(event);
        mRemoteUsersData.addCreatedEvent(mCurrentUserId, eventId, event);
    }
}
