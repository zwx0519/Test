package com.example.test.presenter.shop.me.address;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressBean;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.me.address.AddressDeteleBean;
import com.example.test.model.bean.shop.me.address.AddressListBean;
import com.example.test.model.shop.me.address.AddressModel;
import com.example.test.view.shop.me.address.IAddAddress;
import com.example.test.view.shop.me.address.IAddress;

import java.util.HashMap;

public class AddressPresenter  extends BasePresenter<IAddAddress.View> implements IAddAddress.Presenter {
    IAddAddress.View view;
    IAddAddress.BaseModel model;

    public AddressPresenter(IAddAddress.View view) {
        this.view = view;
        model=new AddressModel();
    }

    //获取省市列表
    @Override
    public void getAddressList() {
        if (view != null) {
            model.getAddressList(new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getAddressListReturn((AddressListBean) o);
                }
            });
        }
    }

    //删除地址
    @Override
    public void postAddressDelete(String parentId) {
        if (view != null) {
            model.postAddressDelete(parentId, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postAddressDeleteReturn((AddressDeteleBean) o);
                }
            });
        }
    }
}
