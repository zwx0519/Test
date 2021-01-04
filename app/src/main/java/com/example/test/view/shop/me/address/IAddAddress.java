package com.example.test.view.shop.me.address;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressDeteleBean;
import com.example.test.model.bean.shop.me.address.AddressListBean;

public interface IAddAddress {
    //Address业务下的View
    interface View extends IBaseView {
        void getAddressListReturn(AddressListBean result);

        void postAddressDeleteReturn(AddressDeteleBean result);
    }

    //Address业务下的Presenter
    interface Presenter extends IBasePersenter<IAddAddress.View> {
        void getAddressList();

        void postAddressDelete(String parentId);
    }

    //Address业务下的Model
    interface BaseModel extends IBaseModel {
        void getAddressList(Callback callback);

        void postAddressDelete(String parentId, Callback callback);
    }
}
