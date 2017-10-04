package com.teamup.mihaylov.teamup.Events.CreateEvent;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteData;
import com.teamup.mihaylov.teamup.base.models.Event;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 4.10.2017 г..
 */

@Module()
public class CreateEventModule {
    @Provides
    CreateEventContracts.Presenter provideCreateEventPresenter(AuthenticationProvider authProvider, RemoteData<Event> data){
        return new CreateEventPresenter(authProvider, data);
    }
}
