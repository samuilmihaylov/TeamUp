package com.teamup.mihaylov.teamup.SignIn;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public class SignInModule {
    @Provides
    SignInContracts.Presenter provideSignInPresenter() {
        return new SignInPresenter();
    }
}
