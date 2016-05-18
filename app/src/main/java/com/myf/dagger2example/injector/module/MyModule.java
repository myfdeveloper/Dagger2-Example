package com.myf.dagger2example.injector.module;

import android.app.Application;

import dagger.Module;
import dagger.Provides;

/**
 * Created by MaoYouFeng on 2016/5/17.
 */
@Module
public class MyModule {
    private Application application;

    public MyModule(Application application){
        this.application=application;
    }

    @Provides
    public Application provideApplication(){
        return application;
    }
}
