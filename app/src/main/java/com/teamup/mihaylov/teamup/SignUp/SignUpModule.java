package com.teamup.mihaylov.teamup.SignUp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 5.10.2017 г..
 */

@Module
public class SignUpModule {
    @Provides
    SignUpContracts.Presenter provideSignUpPresenter() {
        return new SignUpPresenter();
    }
}
