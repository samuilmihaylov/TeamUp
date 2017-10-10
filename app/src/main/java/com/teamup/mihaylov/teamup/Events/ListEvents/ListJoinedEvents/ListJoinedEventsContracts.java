package com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents;

import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samui on 6.10.2017 г..
 */

public interface ListJoinedEventsContracts {
    interface View extends BaseContracts.View<Presenter> {

        void setEvents(List<Event> events);
    }

    interface Presenter extends BaseContracts.Presenter<View> {
        void load();
    }
}