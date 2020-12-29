package com.example.test.presenter.shop.me.headportrait;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.headportrait.HeadPortraitBean;
import com.example.test.model.shop.me.headportrait.UpdateUserInfoModel;
import com.example.test.view.shop.me.address.IAddress;
import com.example.test.view.shop.me.headportrait.IUpdateUserInfo;

import java.util.HashMap;

public class UpdateUserInfoPresenter extends BasePresenter<IUpdateUserInfo.View> implements IUpdateUserInfo.Presenter{
    IUpdateUserInfo.View view;
    IUpdateUserInfo.BaseModel model;

    public UpdateUserInfoPresenter(IUpdateUserInfo.View view) {
        this.view = view;
        model=new UpdateUserInfoModel();
    }

    @Override
    public void postUpdateUserInfo(HashMap<String, String> map) {
        if(view!=null){
            model.postUpdateUserInfo(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postUpdateUserInfoReturn((HeadPortraitBean) o);
                }
            });
        }
    }
}
