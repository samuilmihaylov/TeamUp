package com.teamup.mihaylov.teamup.base.data;

import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module()
public class DataModule {
    @Provides
    BaseData<Event> provideRemoteEventsData() {
        return new RemoteEventsData<>();
    }

    @Provides
    BaseData<User> provideRemoteUsersData() {
        return new RemoteUsersData<>();
    }
}
