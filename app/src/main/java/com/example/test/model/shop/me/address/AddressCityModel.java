package com.example.test.model.shop.me.address;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.me.address.IAddress;

public class AddressCityModel extends BaseModel implements IAddress.BaseModel {

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
}
