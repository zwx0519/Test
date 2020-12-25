package com.example.test.presenter.shop.me.address;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.me.address.AddressCityBean;
import com.example.test.model.bean.shop.shoppingcar.UpdateShoppingCarBean;
import com.example.test.model.shop.me.address.AddressCityModel;
import com.example.test.view.shop.me.address.IAddress;
import com.example.test.view.shop.special.ISpecial;

public class AddressCityPresenter extends BasePresenter<IAddress.View> implements IAddress.Presenter {

    IAddress.View view;
    IAddress.BaseModel model;

    public AddressCityPresenter(IAddress.View view) {
        this.view = view;
        model=new AddressCityModel();
    }

    @Override
    public void getAddressCity(int parentId) {
        if (view != null) {
            model.getAddressCity(parentId, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getAddressCityReturn((AddressCityBean) o);
                }
            });
        }
    }

}