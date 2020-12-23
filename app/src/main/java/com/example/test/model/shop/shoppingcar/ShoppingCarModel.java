package com.example.test.model.shop.shoppingcar;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.shoppingcar.AddShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.DeleteShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.model.bean.shop.shoppingcar.UpdateShoppingCarBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.shoppingcar.IShoppingCar;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public void postUpdateShoppingCar(Map<String, String> map, Callback callback) {
        addDisposable(HttpManager.getInstance().getService().postUpdateShoppingCar(map)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<UpdateShoppingCarBean>(callback) {
                    @Override
                    public void onNext(UpdateShoppingCarBean updateShoppingCarBean) {
                        callback.success(updateShoppingCarBean);
                    }
                }));
    }

    @Override
    public void postDeleteShoppingCar(String pIds, Callback callback) {
        addDisposable(HttpManager.getInstance().getService().postDeleteShoppingCar(pIds)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<DeleteShoppingCarBean>(callback) {
                    @Override
                    public void onNext(DeleteShoppingCarBean deleteShoppingCarBean) {
                        callback.success(deleteShoppingCarBean);
                    }
                }));
    }

    //购物车
    @Override
    public void AddShoppingCar(HashMap<String, String> map, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postAddShoppingCar(map)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<AddShoppingCarBean>(callback) {
                            @Override
                            public void onNext(AddShoppingCarBean addShoppingCarBean) {
                                callback.success(addShoppingCarBean);
                            }
                        })
        );
    }
}
