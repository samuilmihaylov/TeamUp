package com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;
import com.teamup.mihaylov.teamup.base.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * Created by samui on 6.10.2017 г..
 */

public class ListCreatedEventsPresenter implements ListCreatedEventsContracts.Presenter {
    private final RemoteUsersData<User> mUsersData;
    private final AuthenticationProvider mAuth;
    private final BaseSchedulerProvider mScheduleProvider;

    private ListCreatedEventsContracts.View mView;
    private ListCreatedEventsContracts.ViewState mViewState;

    private boolean mHasCache;

    public ListCreatedEventsPresenter(
            AuthenticationProvider authProvider,
            RemoteUsersData<User> usersData,
            BaseSchedulerProvider schedulerProvider) {
        mUsersData = usersData;
        mAuth = authProvider;
        mScheduleProvider = schedulerProvider;
    }

    @Override
    public void load() {
//        Observable observable = mHasCache
//                ? mUsersData.getCreatedEvents(mAuth.getUserId())
//                : mCachedEventsData.getCreatedEvents(mAuth.getUserId());
//
//        mHasCache = true;
//
        Observable<List<Event>> observable = mUsersData.getCreatedEvents(mAuth.getUserId());

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
    public void subscribe(ListCreatedEventsContracts.View view) {
        mView = view;
        load();
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }

//    public void setViewState(ListCreatedEventsContracts.ViewState viewState) {
//        mViewState = viewState;
//    }
//
//    public ListCreatedEventsContracts.ViewState getViewState() {
//        return mViewState;
//    }
}
