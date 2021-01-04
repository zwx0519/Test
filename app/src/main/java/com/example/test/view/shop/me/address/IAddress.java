package com.example.test.view.shop.me.address;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressBean;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.me.address.AddressDeteleBean;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.view.shop.special.ISpecial;

import java.util.HashMap;

public interface IAddress {

    //Address业务下的View
    interface View extends IBaseView {
        void getAddressCityReturn(AddressCityBean result);

        void postAddressReturn(AddressBean result);

    }

    //Address业务下的Presenter
    interface Presenter extends IBasePersenter<IAddress.View> {
        void getAddressCity(int parentId);

        void postAddress(HashMap<String,String> map);
    }

    //Address业务下的Model
    interface BaseModel extends IBaseModel {
        void getAddressCity(int parentId, Callback callback);

        void postAddress(HashMap<String,String> map,Callback callback);
    }
}
