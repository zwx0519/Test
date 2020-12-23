package com.example.test.view.shop.shoppingcar;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.shoppingcar.AddShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.DeleteShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.UpdateShoppingCarBean;

import java.util.HashMap;
import java.util.Map;

public interface IShoppingCar {

    interface View extends IBaseView {
        //购物车列表
        void getShoppingCarReturn(ShoppingCarBean shoppingCarBean);

        //更新购物车的数据
        void postUpdateShoppingCarReturn(UpdateShoppingCarBean updateShoppingCarBean);

        //删除购物车数据
        void postDeleteShoppingCarReturn(DeleteShoppingCarBean deleteShoppingCarBean);

        //添加购物车
        void postAddShoppingCarReturn(AddShoppingCarBean rsult);
    }

    interface Presenter extends IBasePersenter<IShoppingCar.View> {
        //购物车列表
        void getShoppingCar();

        //更新购物车的数据
        void postUpdateShoppingCar(Map<String,String> map);

        //删除购物车数据
        void postDeleteShoppingCar(String pIds);

        //添加购物车
        void AddShoppingCar(HashMap<String,String> map);
    }

    interface Model extends IBaseModel {
        //购物车列表
        void getShoppingCar(Callback callback);

        //更新购物车的数据
        void postUpdateShoppingCar(Map<String,String> map, Callback callback);

        //删除购物车数据
        void postDeleteShoppingCar(String pIds,Callback callback);

        //添加购物车
        void AddShoppingCar(HashMap<String, String> map, Callback callback);
    }
}
