package com.teamup.mihaylov.teamup.base.dagger;

import android.app.Application;
import android.content.Context;

import dagger.Binds;
import dagger.Module;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public abstract class ApplicationModule {
    /**
     * Binding the application context
     * @param application the application
     * @return the ApplicationContext
     */
    @Binds
    abstract Context bindContext(Application application);
}