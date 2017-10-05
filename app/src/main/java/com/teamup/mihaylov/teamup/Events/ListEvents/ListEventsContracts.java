package com.teamup.mihaylov.teamup.Events.ListEvents;

import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by samui on 5.10.2017 г..
 */

public interface ListEventsContracts {
    interface View extends BaseContracts.View<Presenter> {

        void setEvents(ArrayList<Event> events);
    }

    interface Presenter extends BaseContracts.Presenter<View> {
        void load();
    }
}