package com.teamup.mihaylov.teamup.Events.ListEvents.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

/**
 * Created by samui on 29.9.2017 г..
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.MyViewHolder> {

    private ArrayList<Event> mEventsList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView eventDescription;
        public TextView eventLocation;

        public MyViewHolder(View view) {
            super(view);
            eventName = (TextView) view.findViewById(R.id.event_name);
            eventDescription = (TextView) view.findViewById(R.id.event_description);
            eventLocation = (TextView) view.findViewById(R.id.event_location);
        }
    }

    public EventsAdapter(ArrayList<Event> events) {
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

        holder.eventName.setText("Name: " + event.getName());
        holder.eventDescription.setText("Description: " + event.getDescription());
        holder.eventLocation.setText("Location: " + event.getLocation());
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }
}
