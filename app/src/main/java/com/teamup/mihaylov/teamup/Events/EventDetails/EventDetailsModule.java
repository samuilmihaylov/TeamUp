package com.teamup.mihaylov.teamup.Events.EventDetails;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteData;
import com.teamup.mihaylov.teamup.base.models.Event;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 5.10.2017 г..
 */

@Module()
public class EventDetailsModule {
    @Provides
    EventDetailsContracts.Presenter provideEventDetailsPresenter(AuthenticationProvider authProvider, RemoteData<Event> data) {
        return new EventDetailsPresenter(authProvider, data);
    }
}
