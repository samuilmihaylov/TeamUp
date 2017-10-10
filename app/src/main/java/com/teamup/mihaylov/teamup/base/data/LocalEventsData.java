package com.teamup.mihaylov.teamup.base.data;

import org.greenrobot.greendao.AbstractDao;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

public class LocalEventsData<T> implements BaseData<T> {

    private final AbstractDao<T, String> mDao;

    @Inject
    public LocalEventsData(AbstractDao<T, String> dao) {
        mDao = dao;
    }

    @Override
    public Observable<List<T>> getAll() {
        List<T> itemsList = mDao.loadAll();
        return Observable.just(itemsList);
    }

    public Observable<List<T>> getJoinedEvents(String userId){
        List<T> itemsList = new ArrayList<>();

        return Observable.just(itemsList);
    }

    public Observable<List<T>> getCreatedEvents(String userId){
//        List<T> itemsList = mDao.queryBuilder()
//                .where(EventDao.Properties.AuthorId.eq(userId))
//                .list();

//        return Observable.just(itemsList);
        return  null;
    }

    @Override
    public void add(T item) {
        Observable.just(mDao.insert(item));
    }

    @Override
    public Observable<T> remove(T item) {
        mDao.delete(item);
        return Observable.just(item);
    }

    public void clear() {
        mDao.deleteAll();
    }
}
