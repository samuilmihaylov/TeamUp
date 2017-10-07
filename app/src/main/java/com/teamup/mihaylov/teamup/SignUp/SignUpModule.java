package com.teamup.mihaylov.teamup.SignUp;

import com.teamup.mihaylov.teamup.base.data.RemoteUsersData;
import com.teamup.mihaylov.teamup.base.models.User;

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
