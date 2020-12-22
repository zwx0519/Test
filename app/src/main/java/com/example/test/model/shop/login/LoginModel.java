package com.example.test.model.shop.login;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.login.ILogin;

public class LoginModel extends BaseModel implements ILogin.Model {

    @Override
    public void potLogin(String username, String pw, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postLogin(username, pw)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<LoginBean>(callback) {
                            @Override
                            public void onNext(LoginBean loginBean) {
                                callback.success(loginBean);
                            }
                        })
        );

    }

}
