package com.teamup.mihaylov.teamup.Events.CreateEvent;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by samui on 4.10.2017 г..
 */

public class CreateEventPresenter implements CreateEventContracts.Presenter {

    private final RemoteEventsData<Event> mRemoteEventsData;
    private final AuthenticationProvider mAuthProvider;
    private final RemoteUsersData<User> mRemoteUsersData;

    private final String mCurrentUserName;
    private final String mCurrentUserId;

    private CreateEventContracts.View mView;

    @Inject
    public CreateEventPresenter(final AuthenticationProvider authProvider,
                                final RemoteEventsData<Event> remoteEventsData,
                                final RemoteUsersData<User> remoteUsersData) {
        mAuthProvider = authProvider;
        mRemoteEventsData = remoteEventsData;
        mRemoteUsersData = remoteUsersData;

        mCurrentUserName = mAuthProvider.getUser().getDisplayName();
        mCurrentUserId = mAuthProvider.getUser().getUid();
    }

    @Override
    public void subscribe(final CreateEventContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void addEvent(final String eventName,
                         final String eventDescription,
                         final String sport,
                         final Integer playersCount,
                         final String date,
                         final String time,
                         final String mLocation,
                         final ArrayList<Double> mCoordinates) {

        final String eventId = mRemoteEventsData.getKey();
        ArrayList<String> participants = new ArrayList<>();
        participants.add(mCurrentUserId);

        Event event = new Event(
                eventId,
                eventName,
                eventDescription,
                sport,
                playersCount,
                date,
                time,
                mCurrentUserName,
                mCurrentUserId,
                participants,
                mLocation,
                mCoordinates);

        mRemoteEventsData.add(event);
        mRemoteUsersData.addCreatedEvent(mCurrentUserId, eventId, event);
    }
}
