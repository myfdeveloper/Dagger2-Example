# Dagger2+Retrofit2+rxjava example #

Dagger2代码写起来很简单，主要是依赖注入框架(即DI框架)的概念比较难理解，要想看懂Dagger2首先要弄懂何为依赖，何为依赖注入，以及注解  
依赖：在A中创建了一个B的对象，说明A对B有一个依赖
依赖注入：正常A要使用B,是在A中new B的对象，如果想在A外面创建对象，在A内使用就需要注入
注解：代表特点功能的字段，如实现注入的@Inject  
  
使用依赖注入的好处之一就是使得代码更加清晰简洁而不臃肿  
主要的注解有：@Inject、@Module 、@Component 、@Provides 、@Scope 、@SubComponent 等，本例中只用到前四个

## 使用方法 ##
首先是添加库和插件
app/build.gradle
```
apply plugin: 'com.neenbedankt.android-apt'

buildscript {
    dependencies {
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
    }
}

dependencies {
    provided 'javax.annotation:javax.annotation-api:1.2'
    compile 'com.google.dagger:dagger:2.0.2'
    apt 'com.google.dagger:dagger-compiler:2.0.2'
}
```
 apt是生成代码的插件，全部都得加上，不然编译不会通过
Component:可以理解为注入器，把标注了@Module的类中创建的对象注入到activity中
```
@Component(modules = {MyModule.class, MyApiModule.class})
public interface MyComponent {
    void inject(MyListActivity activity);
}
```
Module：提供依赖，也可以理解为提供对象的类， @Provides注解的方法返回的对象，就是要提供的对象，即提供依赖
```
@Module
public class MyApiModule {
    @Provides
    public OkHttpClient provideOkHttpClient() {
//provideOkHttpClient方法的命名是特定的provide+返回类名
        ...
    }

    @Provides
    public Retrofit provideRetrofit(Application application, OkHttpClient okHttpClient){
//这里的参数okHttpClient，就是上面方法中返回的对象，其他参数以此类推
        ...
    }

    @Provides
    protected MyApiService provideMyApiService(Retrofit retrofit) {
        ...
    }
}

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
```

```
//DaggerMyComponent类，和myApiModule，myModule方法都是在上面代码编译自动生成的，在build中
public class MyApplication extends Application {
    private static MyApplication sInstance;
    private MyComponent myComponent;
    private void setupCompoent() {
        myComponent = DaggerMyComponent.builder()
                .myApiModule(new MyApiModule())
                .myModule(new MyModule(this))
                .build();
    }
}

在activity中调用就可以开始使用依赖注入了
myComponent.inject(this);

@Inject
    MyApiService myApiService;

注入MyApiService对象，它是MyApiModule类中返回的对象，注入后在activity中就可以直接使用myApiService了
```
Dagger2一些基本的东西讲完了，要详细连接可以参考下面的链接
[当复仇者联盟遇上Dagger2、RxJava和Retrofit的巧妙结合](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0601/2963.html "当复仇者联盟遇上Dagger2、RxJava和Retrofit的巧妙结合")
[详解Dagger2](http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0519/2892.html "详解Dagger2")
[Dagger2 使用初步](http://www.cnblogs.com/zhuyp1015/p/5119727.html)
retrofit,和rxjava理解起来都不难，详细的可以查查网上的文档，这里直接上代码
[Retrofit 2.0 + OkHttp 3.0 配置](http://www.open-open.com/lib/view/open1456904039046.html)
[Rxjava-Android资料汇总](http://blog.csdn.net/qq1026291832/article/details/51007490)
```
public interface MyApiService {
    @Headers("apiKey: "+apiKey)
    @GET("citylist")
    Call<ResponseBody> getInfoData(@Query("cityname") String cityname);
	//ResponseBody为默认的返回类型，可以直接获得json字串，用了Gson可以把数据通过类的形式返回

    @Headers("apiKey: ce1b73c42987efaa6032dc67b39fa05a")
    @GET("citylist")
    Observable<MyInfo> getInfoDataGson(@Query("cityname") String cityname);
//返回类型Observable，是rxjava中的观察者
}

｛
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apis.baidu.com/apistore/weatherservice/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) // 添加Rx适配器
                .addConverterFactory(GsonConverterFactory.create()) // 添加Gson转换器
                .build();
MyApiService myApiService=retrofit.create(MyApiService.class);

//rxjava用法
myApiService.getInfoDataGson(getUser())
                .subscribeOn(Schedulers.io())//由Io现成执行
                .observeOn(AndroidSchedulers.mainThread())//在主线程中回调
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
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e){}
                });
｝
```
butterknife相当于轻量级的dagger,可以省去findViewById 和setOnClickListener等代码
