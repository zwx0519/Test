package com.example.test.presenter.shop.shoppingcar;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.shoppingcar.AddShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.DeleteShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.UpdateShoppingCarBean;
import com.example.test.model.shop.shoppingcar.ShoppingCarModel;
import com.example.test.view.shop.login.ILogin;
import com.example.test.view.shop.shoppingcar.IShoppingCar;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCarPresenter extends BasePresenter<IShoppingCar.View> implements IShoppingCar.Presenter {
    IShoppingCar.View view;
    IShoppingCar.Model model;

    public ShoppingCarPresenter(IShoppingCar.View view) {
        this.view = view;
        model = new ShoppingCarModel();
    }

    @Override
    public void getShoppingCar() {
        if (view != null) {
            model.getShoppingCar(new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getShoppingCarReturn((ShoppingCarBean) o);
                }
            });
        }
    }

    @Override
    public void postUpdateShoppingCar(Map<String, String> map) {
        if (view != null) {
            model.postUpdateShoppingCar(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postUpdateShoppingCarReturn((UpdateShoppingCarBean) o);
                }
            });
        }
    }

    @Override
    public void postDeleteShoppingCar(String pIds) {
        if(view!=null){
            model.postDeleteShoppingCar(pIds, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postDeleteShoppingCarReturn((DeleteShoppingCarBean) o);
                }
            });
        }
    }

    //添加购物车
    @Override
    public void AddShoppingCar(HashMap<String, String> map) {
        if(view!=null){
            model.AddShoppingCar(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postAddShoppingCarReturn((AddShoppingCarBean) o);
                }
            });
        }
    }

}
