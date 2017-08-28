package com.anythingintellect.jakesgit.di;

import dagger.Module;

/**
 * Created by ishan.dhingra on 26/08/17.
 */

@Module(includes = {NetworkModule.class, PersistenceModule.class})
public class BaseModule {

}
