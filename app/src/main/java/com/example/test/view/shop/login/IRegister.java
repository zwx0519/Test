package com.example.test.view.shop.login;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.model.bean.shop.login.RegisterBean;

public interface IRegister {
    interface View extends IBaseView {
        void postRegisterReturn(RegisterBean result);
    }

    interface Presenter extends IBasePersenter<IRegister.View> {
        void postRegister(String username,String pw);
    }


    interface Model extends IBaseModel {
        void potRegister(String username,String pw, Callback callback);
    }
}
