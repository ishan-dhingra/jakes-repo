package com.anythingintellect.jakesgit.util;

import com.anythingintellect.jakesgit.di.NetworkModule;
import com.anythingintellect.jakesgit.network.GitAPIService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by ishan.dhingra on 30/08/17.
 */

@Module
public class MockNetworkModule extends NetworkModule {

    public MockNetworkModule() {
        super(Constants.API_BASE_URL);
    }


    @Singleton
    @Provides
    public GitAPIService providesGitAPIService(Retrofit retrofit) {
        return new MockGitAPIService();
    }

}
