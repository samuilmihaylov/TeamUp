package com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.User;
import com.teamup.mihaylov.teamup.base.utils.schedulers.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 6.10.2017 г..
 */

@Module()
public class ListCreatedEventsModule {

    @Provides
    ListCreatedEventsContracts.Presenter provideListCreatedEventsPresenter(AuthenticationProvider authProvider,
                                                                           RemoteUsersData<User> data,
                                                                           BaseSchedulerProvider schedulerProvider) {
        return new ListCreatedEventsPresenter(authProvider, data, schedulerProvider);
    }
}
