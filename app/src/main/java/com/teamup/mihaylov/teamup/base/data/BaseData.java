package com.teamup.mihaylov.teamup.base.data;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

public abstract class BaseData<T> {
    public abstract Observable<T[]> getAll();
    public abstract Observable<T> getById(String id);
    public abstract Observable<T> add(T item);
    public abstract Observable<T> remove(T item);
}
