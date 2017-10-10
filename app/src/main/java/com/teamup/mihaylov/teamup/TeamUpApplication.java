package com.teamup.mihaylov.teamup;

import com.teamup.mihaylov.teamup.base.BaseActivity;
import com.teamup.mihaylov.teamup.base.contracts.CurrentActivityProvider;
import com.teamup.mihaylov.teamup.base.dagger.AppComponent;
import com.teamup.mihaylov.teamup.base.dagger.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by samui on 3.10.2017 г..
 */

public class TeamUpApplication extends DaggerApplication implements CurrentActivityProvider {
    private BaseActivity mCurrentActivity;

    @Override
    public void onCreate() {
        super.onCreate();

//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "notes-db");
//        Database db = helper.getWritableDb();
//        mDaoSession = new DaoMaster(db).newSession();
    }


//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        AppComponent appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    public void setCurrentActivity(BaseActivity activity) {
        mCurrentActivity = activity;
    }

    public BaseActivity getCurrentActivity() {
        return mCurrentActivity;
    }
}