package com.teamup.mihaylov.teamup.base.data;

import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

public abstract class BaseData<T> {

    public abstract Observable<ArrayList<Event>> getAll();

    public abstract Observable<T> getById(String id);

    public abstract void add(T item);

    public abstract Observable<T> remove(T item);
}
