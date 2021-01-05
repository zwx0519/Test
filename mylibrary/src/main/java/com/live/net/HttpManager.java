package com.live.net;
import android.util.Log;


import com.live.model.api.ApiService;
import com.live.utils.SpUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求
 * 整个项目全局使用
 */
public class HttpManager {
    //单例模式
    private static HttpManager instance;
    private ApiService apiService;//网络请求

    private Map<String,Retrofit> map = new HashMap<>();  //retrofit请求对象的对象池

    public static HttpManager getInstance(){
        if(instance == null){
            synchronized(HttpManager.class){//加锁
                if(instance == null){
                    instance = new HttpManager();
                }
            }
        }
        return instance;
    }

    //日志拦截器
    static class LoggingInterceptor implements Interceptor{
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();//Request请求的地址 参数等
            Response response = chain.proceed(request);// Response回复的请求
            ResponseBody responseBody = response.peekBody(Integer.MAX_VALUE);
            Log.i("responseBody",responseBody.string());
            return response;
        }
    }

    //头部拦截器
    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request build = chain.request().newBuilder()
                    .addHeader("Authorization","APPCODE 964e16aa1ae944e9828e87b8b9fbd30a")
                    .addHeader("X-Nideshop-Token", SpUtils.getInstance().getString("token"))
                    .build();

            return chain.proceed(build);
        }
    }

    private OkHttpClient getOkHttpClient(){
        OkHttpClient ok = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new HeaderInterceptor())
                .build();
        return ok;
    }

    //网络请求
    private Retrofit getRetrofit(String url){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(getOkHttpClient())///添加拦截器
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    //使用网络请求
    public ApiService getService(){
        if(apiService==null){
            apiService=getRetrofit(ApiService.BASE_URL).create(ApiService.class);
        }
        return apiService;
    }

}
