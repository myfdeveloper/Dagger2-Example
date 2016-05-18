package com.myf.dagger2example.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.myf.dagger2example.R;
import com.myf.dagger2example.injector.component.MyComponent;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected void setupActivityComponent(MyComponent appComponent) {

    }
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.button)
    public void onClick(){
        startActivity(new Intent(this, MyListActivity.class));
    }
}
