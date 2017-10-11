package com.teamup.mihaylov.teamup.Events.CreateEvent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.data.RemoteEventsData;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by samui on 11.10.2017 г..
 */

public class CreateEventPresenter_Tests {

    @Mock
    RemoteEventsData<Event> mockEventsRepository;

    @Mock
    AuthenticationProvider mockAuthProvider;

    @Mock
    RemoteUsersData<User> mockUsersData;

    @Mock
    CreateEventContracts.View mockView;

    @Mock
    FirebaseAuth mAuth;

    @Mock
    FirebaseUser mUser;

    private CreateEventPresenter mPresenter;
    private Event mEvent;

    @Before
    public void setupTest() {
        MockitoAnnotations.initMocks(this);

        when(mAuth.getCurrentUser())
                .thenReturn(mUser);

        when(mockAuthProvider.getUser())
                .thenReturn(mUser);

        when(mockAuthProvider
                .getUser()
                .getDisplayName())
                .thenReturn("testUserName");

        when(mockAuthProvider
                .getUser()
                .getUid())
                .thenReturn("testUserId");

        when(mockEventsRepository
                .getKey())
                .thenReturn("testId");

        mPresenter = new CreateEventPresenter(mockAuthProvider, mockEventsRepository, mockUsersData);

        mPresenter.subscribe(mockView);

        String eventId = mockEventsRepository.getKey();
        String eventName = "testName";
        String eventDescription = "testDescription";
        String sport =   "testSport";
        Integer playersCount = 1;
        String date = "testDate";
        String time =  "testTime";
        String username = mockAuthProvider.getUser().getDisplayName();
        String userId = mockAuthProvider.getUser().getUid();
        String mLocation = "testLocation";
        ArrayList<Double> mCoordinates = new ArrayList<Double>();
        ArrayList<String> participants = new ArrayList<String>();

        mEvent = new Event(
                eventId,
                eventName,
                eventDescription,
                sport,
                playersCount,
                date,
                time,
                username,
                userId,
                participants,
                mLocation,
                mCoordinates);

        mPresenter.addEvent(
                eventName,
                eventDescription,
                sport,
                playersCount,
                date,
                time,
                mLocation,
                mCoordinates
        );
    }

    @Test
    public void addEvent_shouldCallEventsDataAddMethod() {
        verify(mockEventsRepository).add(mEvent);
    }

    @Test
    public void addEvent_shouldCallUsersDataAddCreatedEventMethod() {
        verify(mockUsersData)
                .addCreatedEvent(mockAuthProvider.getUser().getUid(), mockEventsRepository
                .getKey(), mEvent);
    }
}
