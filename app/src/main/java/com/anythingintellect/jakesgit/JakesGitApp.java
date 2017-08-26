package com.anythingintellect.jakesgit;

import android.app.Application;

import com.anythingintellect.jakesgit.di.AppComponent;
import com.anythingintellect.jakesgit.di.BaseModule;
import com.anythingintellect.jakesgit.di.DaggerAppComponent;
import com.anythingintellect.jakesgit.di.NetworkModule;
import com.anythingintellect.jakesgit.util.Constants;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

public class JakesGitApp extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder()
                .baseModule(new BaseModule())
                .networkModule(new NetworkModule(Constants.API_BASE_URL))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
