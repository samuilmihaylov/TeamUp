package com.teamup.mihaylov.teamup.Events.ListEvents;

import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samui on 5.10.2017 г..
 */

public class ListEventsPresenter implements ListEventsContracts.Presenter {

    private final RemoteEventsData<Event> mRemoteData;
    private ListEventsContracts.View mView;

    @Inject
    ListEventsPresenter(RemoteEventsData<Event> data) {
        mRemoteData = data;
    }

    @Override
    public void load() {
        Observable<ArrayList<Event>> observable = mRemoteData.getAll();

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<Event>>() {
                    @Override
                    public void accept(ArrayList<Event> events) throws Exception {
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
    public void subscribe(ListEventsContracts.View view) {
        mView = view;
        load();
    }

    @Override
    public void unsubscribe() {
        mView = null;
    }
}
