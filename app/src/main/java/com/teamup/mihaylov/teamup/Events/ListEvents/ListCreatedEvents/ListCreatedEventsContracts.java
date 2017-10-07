package com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents;

import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

/**
 * Created by samui on 6.10.2017 г..
 */

public interface ListCreatedEventsContracts {
    interface View extends BaseContracts.View<Presenter> {
        void setEvents(ArrayList<Event> events);
    }

    interface Presenter extends BaseContracts.Presenter<View> {
        void load();
    }
}