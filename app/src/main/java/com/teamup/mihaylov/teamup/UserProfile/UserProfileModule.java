package com.teamup.mihaylov.teamup.UserProfile;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 5.10.2017 г..
 */

@Module
public class UserProfileModule {
    @Provides
    UserProfileContracts.Presenter provideUserProfilePresenter() {
        return new UserProfilePresenter();
    }
}
