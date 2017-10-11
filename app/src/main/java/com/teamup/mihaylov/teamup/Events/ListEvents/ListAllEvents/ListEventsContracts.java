package com.teamup.mihaylov.teamup.Events.ListEvents.ListAllEvents;

import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.List;

/**
 * Created by samui on 5.10.2017 г..
 */

public interface ListEventsContracts {
    interface View extends BaseContracts.View<Presenter> {

        void setEvents(List<Event> events);
    }

    interface Presenter extends BaseContracts.Presenter<View> {
        void load();

        Boolean isAuthenticated();

        String getUid();
    }
}