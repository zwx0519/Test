package com.example.test.model.shop.special;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.special.ISpecial;

public class SpecialModel extends BaseModel implements ISpecial.BaseModel {

    @Override
    public void getSpecial(int page,Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getSpecial(page)
                        .compose(RxUtils.rxScheduler())//线程切换
                .subscribeWith(new CommonSubscriber<SpecialBean>(callback) {
                    @Override
                    public void onNext(SpecialBean specialBean) {
                        callback.success(specialBean);
                    }
                })
        );
    }
}
