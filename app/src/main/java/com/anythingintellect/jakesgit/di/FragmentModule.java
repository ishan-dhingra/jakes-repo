package com.anythingintellect.jakesgit.di;

import android.content.Context;

import dagger.Module;

/**
 * Created by ishan.dhingra on 26/08/17.
 */
@Module
public class FragmentModule {

    private final Context context;

    public FragmentModule(Context context) {
        this.context = context;
    }
}
