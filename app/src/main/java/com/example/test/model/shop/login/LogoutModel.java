package com.example.test.model.shop.login;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.model.bean.shop.login.LogoutBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.login.ILogin;
import com.example.test.view.shop.login.ILogout;

public class LogoutModel extends BaseModel implements ILogout.Model {
    @Override
    public void potLogout(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postlogout()
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<LogoutBean>(callback) {
                            @Override
                            public void onNext(LogoutBean logoutBean) {
                                callback.success(logoutBean);
                            }
                        })
        );
    }
}
