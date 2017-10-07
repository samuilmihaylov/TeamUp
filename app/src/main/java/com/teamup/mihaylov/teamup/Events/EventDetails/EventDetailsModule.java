package com.teamup.mihaylov.teamup.Events.EventDetails;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 5.10.2017 г..
 */

@Module()
public class EventDetailsModule {
    @Provides
    EventDetailsContracts.Presenter provideEventDetailsPresenter(AuthenticationProvider authProvider, RemoteEventsData<Event> eventsData, RemoteUsersData<User> usersData) {
        return new EventDetailsPresenter(authProvider, eventsData, usersData);
    }
}
