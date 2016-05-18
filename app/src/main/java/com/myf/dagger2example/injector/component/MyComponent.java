package com.myf.dagger2example.injector.component;

import com.myf.dagger2example.injector.module.MyApiModule;
import com.myf.dagger2example.injector.module.MyModule;
import com.myf.dagger2example.ui.MyListActivity;

import dagger.Component;

/**
 * Created by MaoYouFeng on 2016/5/17.
 */
@Component(modules = {MyModule.class, MyApiModule.class})
public interface MyComponent {
    void inject(MyListActivity activity);

}
