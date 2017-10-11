package com.teamup.mihaylov.teamup.base.utils.schedulers;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 11.10.2017 г..
 */

@Module
public class SchedulersModule {
    @Provides
    BaseSchedulerProvider provideBaseSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
