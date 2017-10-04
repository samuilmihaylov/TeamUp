package com.teamup.mihaylov.teamup.base.dagger;

import com.teamup.mihaylov.teamup.DrawerNavMain.DrawerNavMainActivity;
import com.teamup.mihaylov.teamup.Events.CreateEvent.CreateEventActivity;
import com.teamup.mihaylov.teamup.Events.CreateEvent.CreateEventModule;
import com.teamup.mihaylov.teamup.Events.EventDetails.EventDetailsActivity;
import com.teamup.mihaylov.teamup.Events.ListEvents.ListEventsActivity;
import com.teamup.mihaylov.teamup.SignIn.SignInActivity;
import com.teamup.mihaylov.teamup.SignIn.SignInModule;
import com.teamup.mihaylov.teamup.SignUp.SignUpActivity;
import com.teamup.mihaylov.teamup.UserProfile.UserProfileActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = { })
    abstract DrawerNavMainActivity drawerNavMainActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = { })
    abstract UserProfileActivity userProfileActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = { SignInModule.class})
    abstract SignInActivity signInActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = { })
    abstract SignUpActivity signUpActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = {CreateEventModule.class})
    abstract CreateEventActivity createEventActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = { })
    abstract ListEventsActivity listEventsActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = { })
    abstract EventDetailsActivity eventDetailsActivity();
}
