package com.teamup.mihaylov.teamup.base.data;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by samui on 11.10.2017 г..
 */

public class LocalUsersData<T> implements BaseData<T> {

    private final AbstractDao<T, String> mDao;

    @Inject
    public LocalUsersData(AbstractDao<T, String> dao) {
        mDao = dao;
    }

    @Override
    public Observable<List<T>> getAll() {
        List<T> itemsList = mDao.loadAll();
        return Observable.just(itemsList);
    }

    @Override
    public void add(T item) {
        mDao.insert(item);
    }

    @Override
    public Observable<T> remove(T item) {
        return null;
    }

    public T getById(String id) {
        return mDao.load(id);
    }

    public void clear() {
        mDao.deleteAll();
    }
}
