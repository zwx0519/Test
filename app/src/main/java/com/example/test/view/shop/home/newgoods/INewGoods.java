package com.example.test.view.shop.home.newgoods;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsBean;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsListBean;

import java.util.HashMap;

//接口契约类
public interface INewGoods {

    interface View extends IBaseView {
        //定义一个被实现的View层接口方法
        void getNewGoodsReturn(NewGoodsBean result);
        void getNewGoodsListReturn(NewGoodsListBean result);

    }

    interface Persenter extends IBasePersenter<INewGoods.View> {
        //定义一个V层调用的接口
        void getNewGoods();
        void getNewGoodsList(HashMap<String,String>map);
    }

    interface Model extends IBaseModel {
        //定义一个加载数据的接口方法   被P层
        void getNewGoods(Callback callback);
        void getNewGoodsList(HashMap<String,String>map,Callback callback);
    }

}