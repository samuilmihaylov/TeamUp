package com.teamup.mihaylov.teamup.base.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by samui on 29.9.2017 г..
 */

public class Event implements Parcelable {
    private String id;
    private String name;
    private String description;
    private String date;
    private String time;
    private String authorName;
    private String authorId;
    private ArrayList<String> participants;

    public Event(){

    }

    public Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        date = in.readString();
        time = in.readString();
        authorName = in.readString();
        authorId = in.readString();
        participants = (ArrayList<String>) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeString(description);
        out.writeString(date);
        out.writeString(time);
        out.writeString(authorName);
        out.writeString(authorId);
        out.writeSerializable(participants);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public Event(
            String id,
            String name,
            String description,
            String date,
            String time,
            String authorName,
            String authorId,
            ArrayList<String> participants) {
        setId(id);
        setName(name);
        setDescription(description);
        setDate(date);
        setTime(time);
        setAuthorName(authorName);
        setAuthorId(authorId);
        setParticipants(participants);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAuthorName() {
        return this.authorName;
    }

    public void setAuthorName(String mAuthorName) {
        this.authorName = mAuthorName;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public void setAuthorId(String mAuthorId) {
        this.authorId = mAuthorId;
    }

    public ArrayList<String> getParticipants(){
        if(this.participants == null){
            this.participants = new ArrayList<>();
        }

        return this.participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public void addParticipant(String id){
        this.participants.add(id);
    }

    public void removeParticipant(String id){
        this.participants.remove(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
