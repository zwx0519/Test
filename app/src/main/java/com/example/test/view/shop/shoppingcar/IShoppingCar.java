package com.example.test.view.shop.shoppingcar;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;

public interface IShoppingCar {

    interface View extends IBaseView {
        void getShoppingCarReturn(ShoppingCarBean shoppingCarBean);
    }

    interface Presenter extends IBasePersenter<IShoppingCar.View> {
        void getShoppingCar();
    }

    interface Model extends IBaseModel {
        void getShoppingCar(Callback callback);
    }
}
