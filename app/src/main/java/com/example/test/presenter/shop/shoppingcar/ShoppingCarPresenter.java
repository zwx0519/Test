package com.example.test.presenter.shop.shoppingcar;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.model.shop.shoppingcar.ShoppingCarModel;
import com.example.test.view.shop.login.ILogin;
import com.example.test.view.shop.shoppingcar.IShoppingCar;

public class ShoppingCarPresenter extends BasePresenter<IShoppingCar.View> implements IShoppingCar.Presenter {
    IShoppingCar.View view;
    IShoppingCar.Model model;

    public ShoppingCarPresenter(IShoppingCar.View view) {
        this.view = view;
        model=new ShoppingCarModel();
    }

    @Override
    public void getShoppingCar() {
        if(view!=null){
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
}
