package com.teamup.mihaylov.teamup.base.authentication;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public class AuthenticationModule {
    @Provides
    AuthenticationProvider providesAuthentication(Context context) {
        return new AuthenticationProvider(context);
    }
}
