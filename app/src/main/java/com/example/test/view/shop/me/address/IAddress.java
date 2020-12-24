package com.example.test.view.shop.me.address;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.view.shop.special.ISpecial;

public interface IAddress {
    //Address业务下的View
    interface View extends IBaseView {
        void getAddressCityReturn(AddressCityBean result);
    }
    //Address业务下的Presenter
    interface Presenter extends IBasePersenter<IAddress.View> {
        void getAddressCity(int parentId);
    }

    //Address业务下的Model
    interface BaseModel extends IBaseModel {
        void getAddressCity(int parentId, Callback callback);
    }
}
