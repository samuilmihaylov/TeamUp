package com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents;

import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.List;

/**
 * Created by samui on 9.10.2017 г..
 */

public class ListCreatedEventsViewState  implements ListCreatedEventsContracts.ViewState {
    private List<Event> mPersons;
    private boolean hHasCache;

    @Override
    public void setEvents(List<Event> persons) {
        mPersons = persons;
    }

    @Override
    public List<Event> getEvents() {
        return mPersons;
    }

    @Override
    public void setHasCache(boolean hasCache) {
        hHasCache = hasCache;
    }

    @Override
    public boolean getHasCache() {
        return hHasCache;
    }
}