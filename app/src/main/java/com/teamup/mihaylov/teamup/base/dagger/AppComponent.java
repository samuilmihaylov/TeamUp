package com.teamup.mihaylov.teamup.base.dagger;

import android.app.Application;

import com.teamup.mihaylov.teamup.ConfigModule;
import com.teamup.mihaylov.teamup.TeamUpApplication;
import com.teamup.mihaylov.teamup.base.authentication.AuthenticationModule;
import com.teamup.mihaylov.teamup.base.data.DataModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by samui on 3.10.2017 г..
 */

@Singleton
@Component(modules = {
        ConfigModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        AuthenticationModule.class,
        DataModule.class
})

public interface AppComponent extends AndroidInjector<DaggerApplication> {
    void inject(TeamUpApplication application);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}