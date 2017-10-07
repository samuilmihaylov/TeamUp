package com.teamup.mihaylov.teamup.base.data;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamup.mihaylov.teamup.TeamUpApplication;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by samui on 3.10.2017 г..
 */

public class RemoteEventsData<T> implements BaseData<T>, ValueEventListener{

    private final DatabaseReference mDatabase;
    private final ArrayList<T> mEventsList;
    private String mKey;

    @Inject
    RemoteEventsData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("events");
        mEventsList = new ArrayList<>();
        mDatabase.addValueEventListener(this);
    }

    @Override
    public Observable<ArrayList<T>> getAll() {
        return Observable.create(new ObservableOnSubscribe<ArrayList<T>>() {
            @Override
            public void subscribe(final ObservableEmitter<ArrayList<T>> emitter) throws Exception {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mEventsList.clear();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Event event = postSnapshot.getValue(Event.class);
                            mEventsList.add((T) event);
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
            mEventsList.add((T)event);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
