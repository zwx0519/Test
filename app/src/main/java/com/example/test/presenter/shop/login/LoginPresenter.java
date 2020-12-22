package com.example.test.presenter.shop.login;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.LoginBean;
import com.example.test.model.shop.login.LoginModel;
import com.example.test.view.shop.login.ILogin;

public class LoginPresenter extends BasePresenter<ILogin.View> implements ILogin.Presenter {
    ILogin.Model model;
    ILogin.View view;

    public LoginPresenter(ILogin.View view) {
        this.view = view;
        model=new LoginModel();
    }

    @Override
    public void postLogin(String username, String pw) {
        model.potLogin(username, pw, new Callback() {
            @Override
            public void fail(String msg) {
                view.tips(msg);
            }

            @Override
            public void success(Object o) {
                if(view!=null){
                    view.postLoginReturn((LoginBean) o);
                }
            }
        });
    }
}
