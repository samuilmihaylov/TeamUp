package com.teamup.mihaylov.teamup.base.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by samui on 3.10.2017 г..
 */

public class RemoteData<T> extends BaseData<T> implements ValueEventListener{

    private final DatabaseReference mDatabase;
    private final ArrayList<Event> mEventsList;
    private String mKey;

    @Inject
    RemoteData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mEventsList = new ArrayList<Event>();
        mDatabase.addValueEventListener(this);
    }

    @Override
    public Observable<ArrayList<Event>> getAll() {
        return Observable.create(new ObservableOnSubscribe<ArrayList<Event>>() {
            @Override
            public void subscribe(final ObservableEmitter<ArrayList<Event>> emitter) throws Exception {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mEventsList.clear();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Event event = postSnapshot.getValue(Event.class);
                            mEventsList.add(event);
                        }
                        emitter.onNext(mEventsList);
                        emitter.onComplete();
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        mDatabase.removeEventListener(this);
                    }
                });
            }
        });
    }

    public void setKey(String key){
        mKey = key;
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


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mEventsList.clear();

        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            Event event = postSnapshot.getValue(Event.class);
            mEventsList.add(event);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
