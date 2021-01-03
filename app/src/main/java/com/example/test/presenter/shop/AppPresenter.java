package com.example.test.presenter.shop;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.AppBean;
import com.example.test.model.shop.AppModel;
import com.example.test.view.shop.IApp;

public class AppPresenter extends BasePresenter<IApp.View> implements IApp.Presenter  {
    IApp.Model model;
    IApp.View view;

    public AppPresenter(IApp.View view) {
        this.view = view;
        model = new AppModel();
    }

    @Override
    public void getAppInfo() {
        model.getAppInfo(new Callback<AppBean>() {
            @Override
            public void success(AppBean data) {
                if(mView != null){
                    mView.getAppInfoReturn(data);
                }
            }

            @Override
            public void fail(String err) {
                view.tips(err);
            }
        });
    }
}
