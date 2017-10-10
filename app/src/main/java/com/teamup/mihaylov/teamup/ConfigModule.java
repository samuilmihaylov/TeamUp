package com.teamup.mihaylov.teamup;

import android.content.Context;

import com.teamup.mihaylov.teamup.base.contracts.CurrentActivityProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 9.10.2017 г..
 */

@Module
public class ConfigModule {
    @Provides
    CurrentActivityProvider providesCurrentActivityProvider(Context context) {
        return ((TeamUpApplication) context.getApplicationContext());
    }
}
