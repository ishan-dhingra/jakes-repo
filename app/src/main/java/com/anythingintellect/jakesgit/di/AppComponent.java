package com.anythingintellect.jakesgit.di;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

@Component(modules = BaseModule.class)
@Singleton
public interface AppComponent {

    FragmentComponent plusFragmentModule(FragmentModule fragmentModule);

}
