package com.teamup.mihaylov.teamup.Events.EventDetails;

import com.teamup.mihaylov.teamup.base.contracts.BaseContracts;
import com.teamup.mihaylov.teamup.base.models.Event;

/**
 * Created by samui on 5.10.2017 г..
 */

public class EventDetailsContracts extends BaseContracts {
    interface View extends BaseContracts.View<Presenter> {

    }

    interface Presenter extends BaseContracts.Presenter<View> {

        void setEvent(Event event);

        void setEventId(String eventId);

        Event getEvent();

        Boolean isParticipating();

        Boolean isAuthor();

        void joinEvent();

        void leaveEvent();
    }
}
