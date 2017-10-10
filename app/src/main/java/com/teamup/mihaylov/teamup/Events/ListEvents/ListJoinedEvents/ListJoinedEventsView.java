package com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents;

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

import com.teamup.mihaylov.teamup.Events.EventDetails.EventDetailsActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.base.EventsAdapter;
import com.teamup.mihaylov.teamup.Events.ListEvents.base.RecyclerItemListener;
import com.teamup.mihaylov.teamup.R;
import com.teamup.mihaylov.teamup.base.models.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samui on 6.10.2017 г..
 */

public class ListJoinedEventsView extends Fragment implements ListJoinedEventsContracts.View {

    private List<Event> mEventsList;
    private RecyclerView mRecyclerView;
    private EventsAdapter mAdapter;
    private ListJoinedEventsContracts.Presenter mPresenter;

    public ListJoinedEventsView() {
        // Required empty public constructor
    }

    public static ListJoinedEventsView newInstance() {
        return new ListJoinedEventsView();
    }

    @Override
    public void setPresenter(ListJoinedEventsContracts.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_joined_events, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.events_list);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);

        mEventsList = new ArrayList<>();
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

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
        mPresenter = null;
    }

    @Override
    public void setEvents(List<Event> events) {
        mEventsList.clear();
        mEventsList.addAll(events);
        mAdapter.notifyDataSetChanged();
    }
}