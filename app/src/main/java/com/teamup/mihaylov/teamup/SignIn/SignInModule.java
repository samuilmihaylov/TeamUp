package com.teamup.mihaylov.teamup.SignIn;

import com.teamup.mihaylov.teamup.base.authentication.AuthenticationProvider;
import com.teamup.mihaylov.teamup.base.utils.schedulers.BaseSchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public class SignInModule {
    @Provides
    SignInContracts.Presenter provideSignInPresenter(
            AuthenticationProvider authProvider,
            BaseSchedulerProvider schedulerProvider) {
        return new SignInPresenter(authProvider, schedulerProvider);
    }
}
