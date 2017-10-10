package com.teamup.mihaylov.teamup.base.data;

import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

public interface BaseData<T> {

    Observable<List<T>> getAll();

    void add(T item);

    Observable<T> remove(T item);
}
