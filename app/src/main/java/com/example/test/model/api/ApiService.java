package com.example.test.model.api;

import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.home.brand.BrandNameBean;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailBean;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailListBean;
import com.example.test.model.bean.shop.home.channel.ChannelBean;
import com.example.test.model.bean.shop.home.channel.ChannelTypeBean;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsBean;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsListBean;
import com.example.test.model.bean.shop.special.SpecialBean;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    String BASE_URL = "https://cdplay.cn/";

    @GET("api/index")
    Flowable<HomeBean> getHome();//主页

    @GET("api/catalog/index/")
    Flowable<ChannelBean> getChannel(@Query("id")String url);//分类

    @GET("api/goods/list")//分类数据
    Flowable<ChannelTypeBean> getChannelType(@Query("categoryId")String id);

    @GET("api/topic/list")//专题数据
    Flowable<SpecialBean> getSpecial(@Query("page")int page);

    @GET("api/brand/list")//品牌制造商
    Flowable<BrandNameBean> getBrandName(@Query("page")int page,@Query("size")int size);

    @GET("api/brand/detail")//品牌制造商列表详情
    Flowable<BrandNameDetailBean> getBrandNameDetail(@Query("id")String id);

    @GET("api/goods/list")//品牌制造商列表详情条目
    Flowable<BrandNameDetailListBean> getBrandNameDetailList(@Query("brandId")String brandId);

    @GET("api/goods/hot")//新品首发
    Flowable<NewGoodsBean> getNewGoods();

    @GET("api/goods/list")//新品首发列表
    Flowable<NewGoodsListBean> getNewGoodsList(@QueryMap HashMap<String,String> map);

}
