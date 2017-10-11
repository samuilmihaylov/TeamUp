package com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents;

import com.google.firebase.auth.FirebaseUser;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;
import com.teamup.mihaylov.teamup.base.utils.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by samui on 11.10.2017 г..
 */

public class ListCreatedEventsPresenter_Tests {
    List<Event> mEvents;

    private ImmediateSchedulerProvider mScheduleProvider;

    @Mock
    RemoteUsersData<User> mockRepository;

    @Mock
    AuthenticationProvider mockAuthProvider;

    @Mock
    FirebaseUser mUser;

    @Mock
    ListCreatedEventsContracts.View mockView;

    private ListCreatedEventsPresenter mPresenter;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        mScheduleProvider = new ImmediateSchedulerProvider();

        mEvents = new ArrayList<Event>();
        mEvents.add(new Event());

        when(mockRepository.getCreatedEvents(mUser.getUid()))
                .thenReturn(Observable.just(mEvents));

        mPresenter = new ListCreatedEventsPresenter(mockAuthProvider, mockRepository, mScheduleProvider);
        mPresenter.subscribe(mockView);
    }

    @Test
    public void subscribe_shouldCallSetEvents() {
        verify(mockView).setEvents(mEvents);
    }
}
