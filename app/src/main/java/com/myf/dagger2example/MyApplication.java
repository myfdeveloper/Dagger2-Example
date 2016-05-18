package com.myf.dagger2example;

import android.app.Application;

import com.myf.dagger2example.injector.component.DaggerMyComponent;
import com.myf.dagger2example.injector.component.MyComponent;
import com.myf.dagger2example.injector.module.MyApiModule;
import com.myf.dagger2example.injector.module.MyModule;

/**
 * Created by MaoYouFeng on 2016/5/17.
 */
public class MyApplication extends Application {
    private static MyApplication sInstance;
    private MyComponent myComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.sInstance = this;
        setupCompoent();
    }

    private void setupCompoent() {
        //DaggerMyComponent类，和myApiModule，myModule方法都是dagger生成的
        myComponent = DaggerMyComponent.builder()
                .myApiModule(new MyApiModule())
                .myModule(new MyModule(this))
                .build();
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

    public MyComponent getMyComponent() {
        return myComponent;
    }
}
