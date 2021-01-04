package com.example.test.model.shop.me.address;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressBean;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.me.address.IAddress;

import java.util.HashMap;

public class AddressCityModel extends BaseModel implements IAddress.BaseModel {
    //获取地址
    @Override
    public void getAddressCity(int parentId, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getAddressCity(parentId)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<AddressCityBean>(callback) {
                            @Override
                            public void onNext(AddressCityBean addressCityBean) {
                                callback.success(addressCityBean);
                            }
                        })
        );
    }
    //添加地址
    @Override
    public void postAddress(HashMap<String, String> map, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postAddress(map)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<AddressBean>(callback) {
                            @Override
                            public void onNext(AddressBean addressBean) {
                                callback.success(addressBean);
                            }
                        })
        );
    }
}
