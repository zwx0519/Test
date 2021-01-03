package com.example.test.view.shop;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.AppBean;

public interface IApp {
    interface View extends IBaseView {
        void getAppInfoReturn(AppBean appBean);
    }

    interface Presenter extends IBasePersenter<IApp.View> {
        void getAppInfo();
    }


    interface Model extends IBaseModel {
        void getAppInfo(Callback callback);
    }
}
