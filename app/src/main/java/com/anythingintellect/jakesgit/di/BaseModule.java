package com.anythingintellect.jakesgit.di;

import com.anythingintellect.jakesgit.db.LocalDataStore;
import com.anythingintellect.jakesgit.network.GitAPIService;
import com.anythingintellect.jakesgit.repo.GitDataRepository;
import com.anythingintellect.jakesgit.repo.GitDataRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

@Module(includes = {NetworkModule.class, PersistenceModule.class})
public class BaseModule {

    @Provides
    @Singleton
    public GitDataRepository providesGitDataRepository(GitAPIService apiService, LocalDataStore localDataStore) {
        return new GitDataRepositoryImpl(apiService, localDataStore);
    }

}
