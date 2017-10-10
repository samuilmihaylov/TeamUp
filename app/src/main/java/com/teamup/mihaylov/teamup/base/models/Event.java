package com.teamup.mihaylov.teamup.base.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by samui on 29.9.2017 г..
 */

public class Event implements Parcelable {
    private String id;

    private String name;
    private String description;
    private String sport;
    private Integer playersCount;
    private String date;
    private String time;
    private String authorName;
    private String authorId;

//    @Convert(converter = StringConverter.class, columnType = String.class)
    private ArrayList<String> participants;

    private String location;

//    @Convert(converter = DoubleConverter.class, columnType = Double.class)
    private ArrayList<Double> coordinates;

    public Event() {

    }
//
//    public static class StringConverter implements PropertyConverter<ArrayList<String>, String> {
//        @Override
//        public ArrayList<String> convertToEntityProperty(String databaseValue) {
//            if (databaseValue == null) {
//                return null;
//            }
//            else {
//                ArrayList<String> list = (ArrayList<String>) Arrays.asList(databaseValue.split(","));
//                return list;
//            }
//        }
//
//        @Override
//        public String convertToDatabaseValue(ArrayList<String> entityProperty) {
//            if(entityProperty==null){
//                return null;
//            }
//            else{
//                StringBuilder sb= new StringBuilder();
//                for(String link:entityProperty){
//                    sb.append(link);
//                    sb.append(",");
//                }
//                return sb.toString();
//            }
//        }
//    }
//
//    public static class DoubleConverter implements PropertyConverter<ArrayList<Double>, Double> {
//        @Override
//        public ArrayList<Double> convertToEntityProperty(Double databaseValue) {
//            if (databaseValue == null) {
//                return null;
//            }
//            else {
//                ArrayList<Double> list = (ArrayList<Double>) Arrays.asList(databaseValue);
//                return list;
//            }
//        }
//
//        @Override
//        public Double convertToDatabaseValue(ArrayList<Double> entityProperty) {
//            if(entityProperty==null){
//                return null;
//            }
//            else{
//                StringBuilder sb= new StringBuilder();
//                for(Double val:entityProperty){
//                    sb.append(val);
//                    sb.append(",");
//                }
//                return Double.valueOf(sb.toString());
//            }
//        }
//    }

    public Event(Parcel in) {
        id = in.readString();
        name = in.readString();
        description = in.readString();
        sport = in.readString();
        playersCount = in.readInt();
        date = in.readString();
        time = in.readString();
        authorName = in.readString();
        authorId = in.readString();
        participants = (ArrayList<String>) in.readSerializable();
        location = in.readString();
        coordinates = (ArrayList<Double>) in.readSerializable();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeString(description);
        out.writeString(sport);
        out.writeInt(playersCount);
        out.writeString(date);
        out.writeString(time);
        out.writeString(authorName);
        out.writeString(authorId);
        out.writeSerializable(participants);
        out.writeString(location);
        out.writeSerializable(coordinates);
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public Event(String id, String name, String description, String sport, Integer playersCount,
            String date, String time, String authorName, String authorId,
            ArrayList<String> participants, String location, ArrayList<Double> coordinates) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sport = sport;
        this.playersCount = playersCount;
        this.date = date;
        this.time = time;
        this.authorName = authorName;
        this.authorId = authorId;
        this.participants = participants;
        this.location = location;
        this.coordinates = coordinates;
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

    public String getSport() {
        return this.sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Integer getPlayersCount() {
        return this.playersCount;
    }

    public void setPlayersCount(Integer playersCount) {
        this.playersCount = playersCount;
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

    public ArrayList<String> getParticipants() {
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }

        return this.participants;
    }

    public void setParticipants(ArrayList<String> participants) {
        this.participants = participants;
    }

    public void addParticipant(String id) {
        this.participants.add(id);
    }

    public void removeParticipant(String id) {
        this.participants.remove(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<Double> getCoordinates() {
        if (this.coordinates == null) {
            this.coordinates = new ArrayList<>();
        }

        return this.coordinates;
    }

    public void setCoordinates(ArrayList<Double> coordinates) {
        this.coordinates = coordinates;
    }
}
