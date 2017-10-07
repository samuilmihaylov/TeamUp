package com.teamup.mihaylov.teamup.Events.ListEvents;

import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.models.Event;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 5.10.2017 г..
 */
@Module()
public class ListEventsModule {
    @Provides
    ListEventsContracts.Presenter provideListEventsPresenter(RemoteEventsData<Event> data) {
        return new ListEventsPresenter(data);
    }
}
