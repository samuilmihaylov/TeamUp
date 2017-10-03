package com.teamup.mihaylov.teamup.Events.ListEvents;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.teamup.mihaylov.teamup.Events.EventDetails.EventDetailsActivity;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListEventsFragment extends Fragment {

    private DatabaseReference mDatabase;
    private ArrayList<Event> mEventsList;
    private RecyclerView mRecyclerView;
    private EventsAdapter mAdapter;

    public ListEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_events, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference("events");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.events_list);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mEventsList = new ArrayList<>();

        mDatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                mEventsList.clear();

                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    Event event = postSnapshot.getValue(Event.class);
                    mEventsList.add(event);
                }

                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                mEventsList.clear();
//
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Event event = postSnapshot.getValue(Event.class);
//                    mEventsList.add(event);
//                }
//
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                mEventsList.clear();
//
//                Event event = dataSnapshot.getValue(Event.class);
//                mEventsList.add(event);
//
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                mEventsList.clear();
//                Event event = dataSnapshot.getValue(Event.class);
//                mEventsList.add(event);
//
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                mEventsList.clear();
//
//                Event event = dataSnapshot.getValue(Event.class);
//                mEventsList.add(event);
//
//                mAdapter.notifyDataSetChanged();
//            }
        });

        mAdapter = new EventsAdapter(mEventsList);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemListener(getActivity().getApplicationContext(), mRecyclerView,
                new RecyclerItemListener.RecyclerTouchListener() {
                    public void onClickItem(View v, int position) {
                        Toast.makeText(getActivity().getApplicationContext(), mEventsList.get(position).getName(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                        intent.putExtra("event_details", mEventsList.get(position));
                        startActivity(intent);
                    }

                    public void onLongClickItem(View v, int position) {
                        Toast.makeText(getActivity().getApplicationContext(), mEventsList.get(position).getName(), Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                        intent.putExtra("event_details", mEventsList.get(position));
                        startActivity(intent);
                    }
                }));

        return view;
    }
}
