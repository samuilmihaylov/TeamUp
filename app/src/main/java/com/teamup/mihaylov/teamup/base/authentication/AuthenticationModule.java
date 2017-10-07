package com.teamup.mihaylov.teamup.base.authentication;

import android.content.Context;

import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public class AuthenticationModule {
    @Provides
    AuthenticationProvider providesAuthentication(Context context, RemoteUsersData<User> usersData) {
        return new AuthenticationProvider(context, usersData);
    }
}
