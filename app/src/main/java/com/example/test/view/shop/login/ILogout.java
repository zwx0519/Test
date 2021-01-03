package com.example.test.view.shop.login;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.model.bean.shop.login.LogoutBean;

//TODO 退出登录
public interface ILogout {
    interface View extends IBaseView {
        void postLogoutReturn(LogoutBean result);
    }

    interface Presenter extends IBasePersenter<ILogout.View> {
        void postLogout();
    }

    interface Model extends IBaseModel {
        void potLogout(Callback callback);
    }
}
