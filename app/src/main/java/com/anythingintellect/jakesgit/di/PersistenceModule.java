package com.anythingintellect.jakesgit.di;

import android.content.Context;

import com.anythingintellect.jakesgit.db.LocalDataStore;
import com.anythingintellect.jakesgit.db.RealmLocalDataStore;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ishan.dhingra on 28/08/17.
 */

@Module
public class PersistenceModule {

    @Provides
    @Singleton
    public LocalDataStore providesLocalDataStore() {
        return new RealmLocalDataStore();
    }


}
