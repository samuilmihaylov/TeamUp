package com.teamup.mihaylov.teamup.base.data;

import com.teamup.mihaylov.teamup.base.models.Event;
import com.teamup.mihaylov.teamup.base.models.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by samui on 3.10.2017 г..
 */

@Module
public class DataModule {

//    @Provides
//    AbstractDao<Event, String> provideEventDao(Context context) {
//        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "events-db");
//        Database db = helper.getWritableDb();
//        DaoSession daoSession = new DaoMaster(db).newSession();
//        return daoSession.getEventDao();
//    }

    @Provides
    BaseData<User> provideRemoteUsersData() {
        return new RemoteUsersData<>();
    }

    @Provides
    BaseData<Event> provideRemoteEventsData() {
        return new RemoteEventsData<>();
    }

//    @Provides
//    BaseData<Event> provideLocalEventsData(AbstractDao<Event, String> dao) {
//        return new LocalEventsData<>(dao);
//    }
}
