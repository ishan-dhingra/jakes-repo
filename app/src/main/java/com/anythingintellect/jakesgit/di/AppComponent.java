package com.anythingintellect.jakesgit.di;

import dagger.Component;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

@Component(modules = BaseModule.class)
public interface AppComponent {

    FragmentComponent plusFragmentModule(FragmentModule fragmentModule);

}
