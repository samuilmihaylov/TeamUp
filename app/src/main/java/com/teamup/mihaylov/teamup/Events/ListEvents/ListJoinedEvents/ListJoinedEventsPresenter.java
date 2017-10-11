package com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;
import com.teamup.mihaylov.teamup.base.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by samui on 6.10.2017 г..
 */

public class ListJoinedEventsPresenter implements ListJoinedEventsContracts.Presenter {
    private final RemoteUsersData<User> mUsersData;
    private final AuthenticationProvider mAuth;
    private final BaseSchedulerProvider mScheduleProvider;

    private ListJoinedEventsContracts.View mView;

    @Inject
    public ListJoinedEventsPresenter(AuthenticationProvider authProvider,
                                     RemoteUsersData<User> usersData,
                                     BaseSchedulerProvider schedulerProvider) {
        mUsersData = usersData;
        mAuth = authProvider;
        mScheduleProvider = schedulerProvider;
    }

    @Override
    public void load() {
        Observable<List<Event>> observable = mUsersData.getJoinedEvents(mAuth.getUserId());

        observable
                .subscribeOn(mScheduleProvider.io())
                .observeOn(mScheduleProvider.ui())
                .subscribe(new Consumer<List<Event>>() {
                    @Override
                    public void accept(List<Event> events) throws Exception {
                        mView.setEvents(events);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    @Override
    public void subscribe(ListJoinedEventsContracts.View view) {
        mView = view;
        load();
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
