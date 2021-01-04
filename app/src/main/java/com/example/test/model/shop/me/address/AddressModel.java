package com.example.test.model.shop.me.address;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressBean;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.me.address.AddressDeteleBean;
import com.example.test.model.bean.shop.me.address.AddressListBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.me.address.IAddAddress;

import java.util.HashMap;

public class AddressModel extends BaseModel implements IAddAddress.BaseModel {
    //地址收货列表
    @Override
    public void getAddressList(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getAddressList()
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<AddressListBean>(callback) {
                            @Override
                            public void onNext(AddressListBean addressListBean) {
                                callback.success(addressListBean);
                            }
                        })
        );
    }
    //删除地址
    @Override
    public void postAddressDelete(String parentId, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postDeleteAddress(parentId)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<AddressDeteleBean>(callback) {
                            @Override
                            public void onNext(AddressDeteleBean addressDeteleBean) {
                                callback.success(addressDeteleBean);
                            }
                        })
        );
    }
}
