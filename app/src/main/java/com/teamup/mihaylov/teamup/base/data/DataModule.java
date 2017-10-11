package com.teamup.mihaylov.teamup.base.data;

import android.content.Context;

import com.teamup.mihaylov.teamup.base.models.DaoMaster;
import com.teamup.mihaylov.teamup.base.models.DaoSession;
import com.teamup.mihaylov.teamup.base.models.DaoUser;
import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public class DataModule {

    @Provides
    AbstractDao<DaoUser, String> providePersonDao(Context context) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "users-db");
        Database db = helper.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        return daoSession.getDaoUserDao();
    }

    @Provides
    BaseData<User> provideRemoteUsersData() {
        return new RemoteUsersData<>();
    }

    @Provides
    BaseData<Event> provideRemoteEventsData() {
        return new RemoteEventsData<>();
    }

    @Provides
    BaseData<DaoUser> provideLocalUsersData(AbstractDao<DaoUser, String> dao) {
        return new LocalUsersData<>(dao);
    }
}
