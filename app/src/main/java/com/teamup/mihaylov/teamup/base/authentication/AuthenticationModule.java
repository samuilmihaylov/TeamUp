package com.teamup.mihaylov.teamup.base.authentication;

import android.content.Context;

import com.teamup.mihaylov.teamup.base.contracts.CurrentActivityProvider;
import com.teamup.mihaylov.teamup.base.data.LocalUsersData;
import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.DaoUser;
import com.teamup.mihaylov.teamup.base.models.User;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public class AuthenticationModule {
    @Provides
    AuthenticationProvider providesAuthentication(
            Context context,
            RemoteUsersData<User> remoteUsersData,
            LocalUsersData<DaoUser> localUsersData,
            CurrentActivityProvider currentActivityProvider) {
        return new AuthenticationProvider(
                context,
                remoteUsersData,
                localUsersData,
                currentActivityProvider);
    }
}
