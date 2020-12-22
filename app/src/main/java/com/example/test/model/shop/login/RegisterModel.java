package com.example.test.model.shop.login;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.model.bean.shop.login.RegisterBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.login.IRegister;

public class RegisterModel extends BaseModel implements IRegister.Model {

    @Override
    public void potRegister(String username, String pw, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postRegister(username, pw)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<RegisterBean>(callback) {
                            @Override
                            public void onNext(RegisterBean registerBean) {
                                callback.success(registerBean);
                            }

                        })
        );
    }
}