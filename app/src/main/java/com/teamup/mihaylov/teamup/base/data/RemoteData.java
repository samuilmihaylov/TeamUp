package com.teamup.mihaylov.teamup.base.data;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by samui on 3.10.2017 г..
 */

public class RemoteData<T> extends BaseData<T> {

    private final DatabaseReference mDatabase;
    private String mKey;

    @Inject
    RemoteData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("events");
    }

    @Override
    public Observable getAll() {
        return null;
    }

    @Override
    public Observable getById(String id) {
        return null;
    }

    public String getKey(){
        mKey = mDatabase.push().getKey();
        return mKey;
    }

    @Override
    public void add(T item) {
        mDatabase.child(mKey).setValue(item);
    }

    @Override
    public Observable remove(Object item) {
        return null;
    }
}
