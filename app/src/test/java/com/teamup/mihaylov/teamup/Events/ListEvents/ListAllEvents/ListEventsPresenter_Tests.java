package com.teamup.mihaylov.teamup.Events.ListEvents.ListAllEvents;

import com.google.firebase.auth.FirebaseUser;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.utils.schedulers.ImmediateSchedulerProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by samui on 11.10.2017 г..
 */

public class ListEventsPresenter_Tests {
    List<Event> mEvents;

    private ImmediateSchedulerProvider mScheduleProvider;

    @Mock
    RemoteEventsData<Event> mockRepository;

    @Mock
    AuthenticationProvider mockAuthProvider;

    @Mock
    FirebaseUser mUser;

    @Mock
    ListEventsContracts.View mockView;

    private ListEventsPresenter mPresenter;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        mScheduleProvider = new ImmediateSchedulerProvider();

        mEvents = new ArrayList<Event>();
        mEvents.add(new Event());

        when(mockRepository.getAll())
                .thenReturn(Observable.just(mEvents));

        mPresenter = new ListEventsPresenter(mockAuthProvider, mockRepository, mScheduleProvider);
        mPresenter.subscribe(mockView);
    }

    @Test
    public void subscribe_shouldCallSetEvents() {
        verify(mockView).setEvents(mEvents);
    }

    @Test
    public void isAuthenticated_whenUserIsNotAuthenticated_shouldReturnFalse() {

        when(mockAuthProvider.getUser())
                .thenReturn(null);

        Boolean result = mPresenter.isAuthenticated();

        assertFalse(result);
    }

    @Test
    public void isAuthenticated_whenUserIsNotAuthenticated_shouldReturnTrue() {

        when(mockAuthProvider.getUser()).thenReturn(mUser);

        Boolean result = mPresenter.isAuthenticated();

        assertTrue(result);
    }
}
