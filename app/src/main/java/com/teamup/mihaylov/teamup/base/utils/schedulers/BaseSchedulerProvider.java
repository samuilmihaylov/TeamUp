package com.teamup.mihaylov.teamup.base.utils.schedulers;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;

/**
 * Created by samui on 11.10.2017 г..
 */

public interface BaseSchedulerProvider {
    /**
     * Provides computation Scheduler
     * @return computation Scheduler
     */
    @NonNull
    Scheduler computation();

    /**
     * Provides Input/Output Scheduler
     * @return Input/Output Scheduler
     */
    @NonNull
    Scheduler io();


    /**
     * Provides ui tread scheduler
     * @return Provides ui tread scheduler
     */
    @NonNull
    Scheduler ui();
}