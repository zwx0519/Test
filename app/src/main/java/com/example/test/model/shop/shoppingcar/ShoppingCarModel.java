package com.example.test.model.shop.shoppingcar;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.shoppingcar.IShoppingCar;

public class ShoppingCarModel extends BaseModel implements IShoppingCar.Model {

    @Override
    public void getShoppingCar(Callback callback) {
        addDisposable(HttpManager.getInstance().getService().getShoppingCar()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<ShoppingCarBean>(callback) {
                    @Override
                    public void onNext(ShoppingCarBean shoppingCarBean) {
                        callback.success(shoppingCarBean);
                    }
                }));
    }
}
