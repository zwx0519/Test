package com.example.test.view.shop.home.brand;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailBean;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailListBean;

public interface IBrand {
    interface View extends IBaseView {
        void getBrand(BrandNameDetailBean result);
        void getBrandList(BrandNameDetailListBean result);
    }

    interface Presenter extends IBasePersenter<IBrand.View> {
        void getBrand(String id);
        void getBrandList(String brandId);
    }

    interface Model extends IBaseModel {
        void getBrand(String id,Callback callback);
        void getBrandList(String brandId,Callback callback);
    }
}
