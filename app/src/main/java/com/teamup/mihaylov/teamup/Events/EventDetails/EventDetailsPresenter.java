package com.teamup.mihaylov.teamup.Events.EventDetails;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteData;
import com.teamup.mihaylov.teamup.base.models.Event;

import javax.inject.Inject;

/**
 * Created by samui on 5.10.2017 г..
 */

public class EventDetailsPresenter implements EventDetailsContracts.Presenter {

    private final AuthenticationProvider mAuthProvider;
    private final RemoteData<Event> mRemoteData;
    private Event mEvent;
    private String mEventId;
    private EventDetailsContracts.View mView;

    @Inject
    EventDetailsPresenter(AuthenticationProvider authProvider, RemoteData<Event> remoteData){
        mAuthProvider = authProvider;
        mRemoteData = remoteData;
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
        this.mEvent.addParticipant(mAuthProvider.getUser().getUid());
        mRemoteData.setKey(mEventId);
        mRemoteData.add(mEvent);
    }

    @Override
    public void leaveEvent(){
        this.mEvent.removeParticipant(mAuthProvider.getUser().getUid());
        mRemoteData.setKey(mEventId);
        mRemoteData.add(mEvent);
    }
}
