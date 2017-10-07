package com.teamup.mihaylov.teamup.base.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by samui on 6.10.2017 г..
 */

public class User implements Parcelable {
    private String id;
    private String name;
    private ArrayList<Event> joinedEvents;
    private ArrayList<Event> createdEvents;

    public User() {

    }

    public User(Parcel in) {
        id = in.readString();
        name = in.readString();
        in.readTypedList(joinedEvents, Event.CREATOR);
        in.readTypedList(createdEvents, Event.CREATOR);
//        joinedEvents = (ArrayList<Event>) in.readSerializable();
//        createdEvents = (ArrayList<Event>) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeTypedList(joinedEvents);
        out.writeTypedList(createdEvents);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public User(
            String id,
            String name,
            ArrayList<Event> joinedEvents,
            ArrayList<Event> createdEvents) {
        setId(id);
        setName(name);
        setJoined(joinedEvents);
        setCreated(createdEvents);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Event> getJoined() {
        if (joinedEvents == null) {
            joinedEvents = new ArrayList<Event>();
        }
        return this.joinedEvents;
    }

    public void setJoined(ArrayList<Event> joinedEvents) {
        this.joinedEvents = joinedEvents;
    }

    public ArrayList<Event> getCreated() {
        if (createdEvents == null) {
            createdEvents = new ArrayList<Event>();
        }
        return this.createdEvents;
    }

    public void setCreated(ArrayList<Event> createdEvents) {
        this.createdEvents = createdEvents;
    }

    public void addJoinedEvent(Event eventId){
        this.joinedEvents.add(eventId);
    }

    public void removeJoinedEvent(Event eventId){
        this.joinedEvents.remove(eventId);
    }

    public void addCreatedEvent(Event eventId){
        this.createdEvents.add(eventId);
    }

    public void removeCreatedEvent(String eventId){
        this.createdEvents.remove(eventId);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
