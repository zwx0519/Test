package com.example.test.model.api;

import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.home.brand.BrandNameBean;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailBean;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailListBean;
import com.example.test.model.bean.shop.home.category.CategoryBean;
import com.example.test.model.bean.shop.home.category.CategoryBottomInfoBean;
import com.example.test.model.bean.shop.home.channel.ChannelBean;
import com.example.test.model.bean.shop.home.channel.ChannelTypeBean;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsBean;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsListBean;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.model.bean.shop.login.RegisterBean;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.model.bean.shop.type.TypeBean;
import com.example.test.model.bean.shop.type.TypeInfBean;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    @GET("api/goods/detail")//居家 商品详情购买页
    Flowable<CategoryBean> getCategory(@Query("id")String id);

    //商品 详情购买页 底部数据列表 api/goods/related?id=1155000
    @GET("api/goods/related")
    Flowable<CategoryBottomInfoBean> getCategoryBottomInfo(@Query("id")String id);

    //分类右边导航
    @GET("api/catalog/index")
    Flowable<TypeBean> getHomeType();

    //用来请求当前分类的列表数据
    @GET("catalog/current")
    Flowable<TypeInfBean> getTypeInfo(@Query("id") String id);

    //用户登录
    @POST("api/auth/login")
    @FormUrlEncoded
    Flowable<LoginBean> postLogin(@Field("username") String username, @Field("password") String pw);

    //用户注册
    @POST("api/auth/register")
    @FormUrlEncoded
    Flowable<RegisterBean> postRegister(@Field("username") String username, @Field("password") String pw);

    //添加到购物车
    @POST("api/cart/add")
    @FormUrlEncoded
    Flowable<LoginBean> postAddCar(@FieldMap HashMap<String,String> map);

    //购物车列表
    @GET("api/cart/index")
    Flowable<ShoppingCarBean> getShoppingCar();
}
