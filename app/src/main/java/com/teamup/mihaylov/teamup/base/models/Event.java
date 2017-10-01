package com.teamup.mihaylov.teamup.base.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by samui on 29.9.2017 г..
 */

public class Event implements Parcelable {
    private String mName;
    private String mDescription;
    private String mDate;
    private String mTime;
    private String mAuthorName;
    private String mAuthorId;

    public Event(){

    }

    public Event(Parcel in) {
        mName = in.readString();
        mDescription = in.readString();
        mDate = in.readString();
        mTime = in.readString();
        mAuthorName = in.readString();
        mAuthorId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mDate);
        dest.writeString(mTime);
        dest.writeString(mAuthorName);
        dest.writeString(mAuthorId);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public Event(String name, String description, String date, String time, String authorName, String authorId) {
        setName(name);
        setDescription(description);
        setDate(date);
        setTime(time);
        setAuthorName(authorName);
        setAuthorId(authorId);
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getDate() {
        return this.mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getTime() {
        return this.mTime;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    public String getAuthorName() {
        return this.mAuthorName;
    }

    public void setAuthorName(String mAuthorName) {
        this.mAuthorName = mAuthorName;
    }

    public String getAuthorId() {
        return this.mAuthorId;
    }

    public void setAuthorId(String mAuthorId) {
        this.mAuthorId = mAuthorId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
