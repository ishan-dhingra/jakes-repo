package com.anythingintellect.jakesgit.di;

import com.anythingintellect.jakesgit.network.GitAPIService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

@Module
public class NetworkModule {

    private final String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton
    @Provides
    public RxJava2CallAdapterFactory providesRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    public JacksonConverterFactory providesJacksonConverterFactory() {
        return JacksonConverterFactory.create();
    }

    @Singleton
    @Provides
    public Retrofit providesRetrofit(RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                                     JacksonConverterFactory jacksonConverterFactory) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(jacksonConverterFactory);
        return builder.build();
    }

    @Singleton
    @Provides
    public GitAPIService providesGitAPIService(Retrofit retrofit) {
        return retrofit.create(GitAPIService.class);
    }

}
