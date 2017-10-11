package com.teamup.mihaylov.teamup.Events.ListEvents.ListAllEvents;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.utils.schedulers.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 5.10.2017 г..
 */
@Module()
public class ListEventsModule {
    @Provides
    ListEventsContracts.Presenter provideListEventsPresenter(AuthenticationProvider authProvider,
                                                             RemoteEventsData<Event> data,
                                                             BaseSchedulerProvider schedulerProvider) {
        return new ListEventsPresenter(authProvider, data, schedulerProvider);
    }
}
