package com.anythingintellect.jakesgit.di;

import com.anythingintellect.jakesgit.view.RepoListFragment;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(RepoListFragment fragment);

}
