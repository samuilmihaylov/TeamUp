package com.teamup.mihaylov.teamup.Events.CreateEvent;

import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;

import java.util.ArrayList;

/**
 * Created by samui on 4.10.2017 г..
 */

public interface CreateEventContracts {

    interface View extends BaseContracts.View<Presenter> {

    }

    interface Presenter extends BaseContracts.Presenter<View> {
        void addEvent(String eventName, String eventDescription, String sport, Integer playersCount, String date, String time, String mLocation, ArrayList<Double> mCoordinates);
    }
}
