package com.example.test.model.shop;

import android.util.Log;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.AppBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.IApp;

public class AppModel extends BaseModel implements IApp.Model {

    @Override
    public void getAppInfo(Callback callback) {
        addDisposable(HttpManager.getInstance().getService().getAppInfo().
                compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<AppBean>(callback) {
                    @Override
                    public void onNext(AppBean appBean) {
                        Log.i("TAG", "model onNext register");
                        callback.success(appBean);
                    }
                }));
    }
}
