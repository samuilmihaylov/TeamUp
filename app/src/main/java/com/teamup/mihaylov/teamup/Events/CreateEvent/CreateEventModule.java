package com.teamup.mihaylov.teamup.Events.CreateEvent;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 4.10.2017 г..
 */

@Module()
public class CreateEventModule {
    @Provides
    CreateEventContracts.Presenter provideCreateEventPresenter(
            AuthenticationProvider authProvider,
            RemoteEventsData<Event> eventsData,
            RemoteUsersData<User> usersData){
        return new CreateEventPresenter(authProvider, eventsData, usersData);
    }
}
