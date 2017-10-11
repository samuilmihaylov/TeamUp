package com.teamup.mihaylov.teamup.base.utils.schedulers;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by samui on 11.10.2017 г..
 */

public final class SchedulerProvider implements BaseSchedulerProvider {

    @Nullable
    private static SchedulerProvider instance;

    private SchedulerProvider() {
    }

    /**
     * Getter for singleton instance of {@link SchedulerProvider}
     *
     * @return the Singleton instance
     */
    public static synchronized SchedulerProvider getInstance() {
        if (instance == null) {
            instance = new SchedulerProvider();
        }

        return instance;
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}