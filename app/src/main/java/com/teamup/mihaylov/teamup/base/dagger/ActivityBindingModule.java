package com.teamup.mihaylov.teamup.base.dagger;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.Events.CreateEvent.CreateEventActivity;
import com.teamup.mihaylov.teamup.Events.CreateEvent.CreateEventModule;
import com.teamup.mihaylov.teamup.Events.EventDetails.EventDetailsActivity;
import com.teamup.mihaylov.teamup.Events.EventDetails.EventDetailsModule;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents.ListCreatedEventsActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListCreatedEvents.ListCreatedEventsModule;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListEventsActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListEventsModule;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents.ListJoinedEventsActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListJoinedEvents.ListJoinedEventsModule;
import com.teamup.mihaylov.teamup.SignIn.SignInActivity;
import com.teamup.mihaylov.teamup.SignIn.SignInModule;
import com.teamup.mihaylov.teamup.SignUp.SignUpActivity;
import com.teamup.mihaylov.teamup.SignUp.SignUpModule;
import com.teamup.mihaylov.teamup.UserProfile.UserProfileActivity;
import com.teamup.mihaylov.teamup.UserProfile.UserProfileModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = {})
    abstract DrawerNavMainActivity drawerNavMainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {UserProfileModule.class})
    abstract UserProfileActivity userProfileActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {SignInModule.class})
    abstract SignInActivity signInActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {SignUpModule.class})
    abstract SignUpActivity signUpActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {CreateEventModule.class})
    abstract CreateEventActivity createEventActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {ListEventsModule.class})
    abstract ListEventsActivity listEventsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {ListJoinedEventsModule.class})
    abstract ListJoinedEventsActivity listJoinedEventsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {ListCreatedEventsModule.class})
    abstract ListCreatedEventsActivity listCreatedEventsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {EventDetailsModule.class})
    abstract EventDetailsActivity eventDetailsActivity();
}
