package com.teamup.mihaylov.teamup.Events.ListEvents.ListAllEvents;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by samui on 5.10.2017 г..
 */

public class ListEventsPresenter implements ListEventsContracts.Presenter {

    private final RemoteEventsData<Event> mRemoteData;
    private final AuthenticationProvider mAuth;
    private final BaseSchedulerProvider mScheduleProvider;

    private ListEventsContracts.View mView;

    @Inject
    ListEventsPresenter(AuthenticationProvider authProvider,
                        RemoteEventsData<Event> data,
                        BaseSchedulerProvider schedulerProvider) {
        mRemoteData = data;
        mAuth = authProvider;
        mScheduleProvider = schedulerProvider;
    }

    @Override
    public void load() {
        Observable<List<Event>> observable = mRemoteData.getAll();

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

    public Boolean isAuthenticated() {
        if (mAuth.getUser() != null) {
            return true;
        }

        return false;
    }

    public String getUid(){
        return mAuth.getUserId();
    }

    @Override
    public void subscribe(ListEventsContracts.View view) {
        mView = view;
        load();
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
