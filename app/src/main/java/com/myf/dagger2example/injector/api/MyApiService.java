package com.myf.dagger2example.injector.api;

import com.myf.dagger2example.data.bean.MyInfo;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MaoYouFeng on 2016/5/17.
 */
public interface MyApiService {
    @Headers("apiKey: ce1b73c42987efaa6032dc67b39fa05a")
    @GET("citylist")
    Observable<MyInfo> getInfoDataGson(@Query("cityname") String cityname);

    @Headers("apiKey: ce1b73c42987efaa6032dc67b39fa05a")
    @GET("citylist")
    Observable<ResponseBody> getInfoData(@Query("cityname") String cityname);
}
