package com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents;

import android.util.Log;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samui on 6.10.2017 г..
 */

public class ListJoinedEventsPresenter implements ListJoinedEventsContracts.Presenter {
    private final RemoteUsersData<User> mUsersData;
    private final AuthenticationProvider mAuth;
    private ListJoinedEventsContracts.View mView;

    @Inject
    public ListJoinedEventsPresenter(AuthenticationProvider authProvider, RemoteUsersData<User> usersData) {
        mUsersData = usersData;
        mAuth = authProvider;
    }

    @Override
    public void load() {
        Observable<List<Event>> observable = mUsersData.getJoinedEvents(mAuth.getUserId());

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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
