package com.teamup.mihaylov.teamup.Events.ListEvents.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.List;

/**
 * Created by samui on 29.9.2017 г..
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private List<Event> mEventsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView eventDate;
        private final TextView eventTime;
        private final TextView eventSport;
        private final TextView eventName;
        private final TextView eventLocation;
        private final TextView eventPeople;

        public MyViewHolder(View view) {
            super(view);
            eventName = (TextView) view.findViewById(R.id.event_name);
            eventSport =  (TextView) view.findViewById(R.id.event_sport);
            eventDate =  (TextView) view.findViewById(R.id.event_date);
            eventTime = (TextView) view.findViewById(R.id.event_time);
            eventPeople = (TextView) view.findViewById(R.id.event_people);
            eventLocation = (TextView) view.findViewById(R.id.event_location);
        }
    }

    public EventsAdapter(List<Event> events) {
        this.mEventsList = events;
    }

    @Override
    public EventsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.MyViewHolder holder, int position) {
        Event event = mEventsList.get(position);

        holder.eventName.setText(event.getName());
        holder.eventSport.setText(event.getSport());
        holder.eventDate.setText(event.getDate());
        holder.eventTime.setText(event.getTime());
        holder.eventLocation.setText(event.getLocation());
        holder.eventPeople.setText(event.getParticipants().size() + " / " + event.getPlayersCount());
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }
}
