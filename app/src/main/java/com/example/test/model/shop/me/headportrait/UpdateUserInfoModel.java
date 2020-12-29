package com.example.test.model.shop.me.headportrait;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.me.headportrait.HeadPortraitBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.me.headportrait.IUpdateUserInfo;

import java.util.HashMap;

public class UpdateUserInfoModel extends BaseModel implements IUpdateUserInfo.BaseModel {

    @Override
    public void postUpdateUserInfo(HashMap<String, String> map, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postUpdateUserInfo(map)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<HeadPortraitBean>(callback) {
                            @Override
                            public void onNext(HeadPortraitBean headPortraitBean) {
                                callback.success(headPortraitBean);
                            }
                        })
        );
    }
}
