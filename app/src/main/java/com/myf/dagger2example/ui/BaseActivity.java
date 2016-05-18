package com.myf.dagger2example.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myf.dagger2example.MyApplication;
import com.myf.dagger2example.injector.component.MyComponent;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        setupActivityComponent(MyApplication.getsInstance().getMyComponent());
    }

    protected abstract void setupActivityComponent(MyComponent appComponent);
    protected abstract int getLayoutId();
}
