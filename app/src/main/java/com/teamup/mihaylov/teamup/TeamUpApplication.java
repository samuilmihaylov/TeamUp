package com.teamup.mihaylov.teamup;

import com.teamup.mihaylov.teamup.base.dagger.AppComponent;
import com.teamup.mihaylov.teamup.base.dagger.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by samui on 3.10.2017 г..
 */

public class TeamUpApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }
}