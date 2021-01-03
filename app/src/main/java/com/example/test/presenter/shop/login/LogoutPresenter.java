package com.example.test.presenter.shop.login;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.model.bean.shop.login.LogoutBean;
import com.example.test.model.shop.login.LogoutModel;
import com.example.test.view.shop.login.ILogin;
import com.example.test.view.shop.login.ILogout;

public class LogoutPresenter extends BasePresenter<ILogout.View> implements ILogout.Presenter{
    ILogout.View view;
    ILogout.Model model;

    public LogoutPresenter(ILogout.View view) {
        this.view = view;
        model=new LogoutModel();
    }

    @Override
    public void postLogout() {
        model.potLogout(new Callback() {
            @Override
            public void fail(String msg) {
                view.tips(msg);
            }

            @Override
            public void success(Object o) {
                if(view!=null){
                    view.postLogoutReturn((LogoutBean) o);
                }
            }
        });
    }
}
