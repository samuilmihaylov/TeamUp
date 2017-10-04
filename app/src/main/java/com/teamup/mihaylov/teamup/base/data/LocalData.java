package com.teamup.mihaylov.teamup.base.data;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

public class LocalData<T> extends BaseData<T> {

    private final ArrayList<T> items;

    public LocalData() {
        this.items = new ArrayList<T>();
    }

    @Override
    public Observable<T[]> getAll() {
        return Observable.just((T[]) this.items.toArray());
    }

    @Override
    public Observable<T> getById(String id) {
        return Observable.just(null);
    }

    @Override
    public void add(T item) {
        this.items.add(item);
    }

    @Override
    public Observable remove(T item) {
        this.items.remove(item);
        return Observable.just(item);
    }
}
