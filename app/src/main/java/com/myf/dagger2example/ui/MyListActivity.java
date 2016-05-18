package com.myf.dagger2example.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.myf.dagger2example.R;
import com.myf.dagger2example.data.bean.MyInfo;
import com.myf.dagger2example.injector.api.MyApiService;
import com.myf.dagger2example.injector.component.MyComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MyListActivity extends BaseActivity {


    @BindView(R.id.rvList)
    RecyclerView rvListView;

    @BindView(R.id.pbLoading)
    ProgressBar pbLoading;

    @Inject
    MyApiService myApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected void setupActivityComponent(MyComponent appComponent) {
        appComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_list;
    }

    private void initView(){
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvListView.setLayoutManager(manager);

        ListAdapter adapter = new ListAdapter();
        rvListView.setAdapter(adapter);
        loadData(adapter);
    }

    private void loadData(final ListAdapter adapter){
        showLoading(true);
        myApiService.getInfoDataGson(getUser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MyInfo>() {
                    @Override
                    public void onNext(MyInfo info) {
                        showLoading(false);
                        if(info!=null &&info.getRetData().size()!=0) {
                            Log.d("info","size-->"+info.getRetData().size());
                            adapter.setInfos((ArrayList<MyInfo.RetDataBean>) info.getRetData());
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e){
                        Log.e("info",e.getMessage());
                        showLoading(false);
                    }
                });
    }



    private String getUser(){
        return "广东";
    }

    public void showLoading(boolean loading) {
        Log.i("info",loading + " Repos");
        pbLoading.setVisibility(loading ? View.VISIBLE : View.GONE);
    }
}
