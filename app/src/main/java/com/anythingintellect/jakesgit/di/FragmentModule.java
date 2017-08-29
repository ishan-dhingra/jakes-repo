package com.anythingintellect.jakesgit.di;

import android.content.Context;

import com.anythingintellect.jakesgit.util.DefaultNavigator;
import com.anythingintellect.jakesgit.util.Navigator;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ishan.dhingra on 26/08/17.
 */
@Module
public class FragmentModule {

    private final Context context;

    public FragmentModule(Context context) {
        this.context = context;
    }

    @PerFragment
    @Provides
    public Navigator providesNavigator() {
        return new DefaultNavigator(context);
    }

}
