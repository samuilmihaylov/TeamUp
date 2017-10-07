package com.teamup.mihaylov.teamup.Events.EventDetails;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import javax.inject.Inject;

/**
 * Created by samui on 5.10.2017 г..
 */

public class EventDetailsPresenter implements EventDetailsContracts.Presenter {

    private final AuthenticationProvider mAuthProvider;
    private final RemoteEventsData<Event> mEventsData;
    private final RemoteUsersData<User> mUsersData;
    private Event mEvent;
    private String mEventId;
    private EventDetailsContracts.View mView;

    @Inject
    EventDetailsPresenter(AuthenticationProvider authProvider,
                          RemoteEventsData<Event> eventsData,
                          RemoteUsersData<User> usersData){
        mAuthProvider = authProvider;
        mEventsData = eventsData;
        mUsersData = usersData;
    }

    @Override
    public void subscribe(EventDetailsContracts.View view) {
        mView = view;
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

    @Override
    public void setEvent(Event event){
        mEvent = event;
    }

    @Override
    public void setEventId(String eventId){
        mEventId = eventId;
    }

    @Override
    public Event getEvent(){
        return mEvent;
    }

    @Override
    public Boolean isParticipating(){
        return true;
    }

    @Override
    public Boolean isAuthor() {
        return this.mEvent.getAuthorId().equals(mAuthProvider.getUser().getUid());
    }

    @Override
    public void joinEvent(){
        String userId = mAuthProvider.getUser().getUid();
        this.mEvent.addParticipant(userId);
        mEventsData.setKey(mEventId);
        mEventsData.add(mEvent);
        mUsersData.addJoinedEvent(userId, mEventId, mEvent);
    }

    @Override
    public void leaveEvent(){
        String userId = mAuthProvider.getUser().getUid();
        this.mEvent.removeParticipant(userId);
        mEventsData.setKey(mEventId);
        mEventsData.add(mEvent);
        mUsersData.removeJoinedEvent(userId, mEventId);
    }
}
