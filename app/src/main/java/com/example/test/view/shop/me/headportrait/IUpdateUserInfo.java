package com.example.test.view.shop.me.headportrait;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.me.headportrait.HeadPortraitBean;
import com.example.test.view.shop.me.address.IAddress;

import java.util.HashMap;
//TODO 更该用户信息
public interface IUpdateUserInfo {

    interface View extends IBaseView {
        void postUpdateUserInfoReturn(HeadPortraitBean result);
    }

    interface Presenter extends IBasePersenter<IUpdateUserInfo.View> {
        void postUpdateUserInfo(HashMap<String,String> map);
    }

    interface BaseModel extends IBaseModel {
        void postUpdateUserInfo(HashMap<String,String> map, Callback callback);
    }
}
