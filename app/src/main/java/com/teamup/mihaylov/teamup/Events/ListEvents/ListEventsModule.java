package com.teamup.mihaylov.teamup.Events.ListEvents;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.models.Event;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 5.10.2017 г..
 */
@Module()
public class ListEventsModule {
    @Provides
    ListEventsContracts.Presenter provideListEventsPresenter(AuthenticationProvider authProvider, RemoteEventsData<Event> data) {
        return new ListEventsPresenter(authProvider, data);
    }
}
