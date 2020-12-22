package com.example.test.presenter.shop.login;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.login.RegisterBean;
import com.example.test.model.shop.login.RegisterModel;
import com.example.test.view.shop.login.ILogin;
import com.example.test.view.shop.login.IRegister;

public class RegisterPresenter extends BasePresenter<IRegister.View> implements IRegister.Presenter {

    IRegister.Model model;
    IRegister.View view;

    public RegisterPresenter(IRegister.View view) {
        this.view = view;
        model=new RegisterModel();
    }

    @Override
    public void postRegister(String username, String pw) {
        if(view!=null){
            model.potRegister(username, pw, new Callback() {
                @Override
                public void fail(String msg) {
                   view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postRegisterReturn((RegisterBean) o);
                }
            });
        }
    }

}
