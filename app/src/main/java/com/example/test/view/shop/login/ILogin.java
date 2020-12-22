package com.example.test.view.shop.login;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.LoginBean;

public interface ILogin {

    interface View extends IBaseView {
        void postLoginReturn(LoginBean result);
    }

    interface Presenter extends IBasePersenter<ILogin.View> {
        void postLogin(String username,String pw);
    }


    interface Model extends IBaseModel {
        void potLogin(String username,String pw, Callback callback);
    }
}
