package com.teamup.mihaylov.teamup.base.data;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by samui on 3.10.2017 г..
 */

public class RemoteUsersData<T> implements BaseData<T>, ValueEventListener{

    private final DatabaseReference mDatabase;
    private final ArrayList<Event> mJoinedEventsList;
    private final ArrayList<Event> mCreatedEventsList;
    private final ArrayList<User> mUsersList;
    private String mKey;
    private DatabaseReference joinedEventsRef;
    private DatabaseReference createdEventsRef;

    @Inject
    RemoteUsersData(){
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        mUsersList = new ArrayList<>();
        mJoinedEventsList = new ArrayList<Event>();
        mCreatedEventsList = new ArrayList<Event>();
        mDatabase.addValueEventListener(this);
    }

    @Override
    public Observable<List<T>> getAll() {
        return Observable.create(new ObservableOnSubscribe<List<T>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<T>> emitter) throws Exception {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mUsersList.clear();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            User user = postSnapshot.getValue(User.class);
                            mUsersList.add((User) user);
                        }
                        emitter.onNext(null);
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

    public Observable<List<Event>> getJoinedEvents(String userId) {
        joinedEventsRef = mDatabase.child(userId).child("joined");
        return Observable.create(new ObservableOnSubscribe<List<Event>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Event>> emitter) throws Exception {
                joinedEventsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mJoinedEventsList.clear();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Event event = postSnapshot.getValue(Event.class);
                            mJoinedEventsList.add(event);
                        }
                        emitter.onNext(mJoinedEventsList);
                        emitter.onComplete();
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        joinedEventsRef.removeEventListener(this);
                    }
                });
            }
        });
    }

    public Observable<List<Event>> getCreatedEvents(String userId) {
        createdEventsRef = mDatabase.child(userId).child("created");
        return Observable.create(new ObservableOnSubscribe<List<Event>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<Event>> emitter) throws Exception {
                createdEventsRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mCreatedEventsList.clear();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Event event = postSnapshot.getValue(Event.class);
                            mCreatedEventsList.add(event);
                        }

                        emitter.onNext(mCreatedEventsList);
                        emitter.onComplete();
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        createdEventsRef.removeEventListener(this);
                    }
                });
            }
        });
    }

    public Observable<User> getById(final String id) {
        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(final ObservableEmitter<User> emitter) throws Exception {
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild(id)) {
                            User user = dataSnapshot.getValue(User.class);
                            emitter.onNext(user);
                        }
                        else {
                            emitter.onNext(null);
                        }
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

    public void update(String key, T value){
        mDatabase.child(mKey).setValue(value);
    }

    @Override
    public void add(T item) {
        mDatabase.child(mKey).setValue(item);
    }

    public void addCreatedEvent(String userId, String eventId, Event event){
        mDatabase.child(userId).child("created").child(eventId).setValue(event);
    }

    public void removeCreatedEvent(String userId, String eventId){
        mDatabase.child(userId).child("created").child(eventId).removeValue();
    }

    public void addJoinedEvent(String userId, String eventId,Event event){
        mDatabase.child(userId).child("joined").child(eventId).setValue(event);
    }

    public void removeJoinedEvent(String userId, String eventId){
        mDatabase.child(userId).child("joined").child(eventId).removeValue();
    }

    @Override
    public Observable remove(Object item) {
        return null;
    }


    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        mUsersList.clear();

        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
            Log.v("data", postSnapshot.toString());
//            User user = postSnapshot.getValue(User.class);
//            mUsersList.add((User)user);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
