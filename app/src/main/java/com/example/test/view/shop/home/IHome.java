package com.example.test.view.shop.home;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.home.brand.BrandNameBean;

public interface IHome {
    //Logic业务下的View
    interface ViewI extends IBaseView {
        void getHomeReturn(HomeBean result);
        void getBrandNameReturn(BrandNameBean result);
    }
    //Logic业务下的Presenter
    interface Presenter extends IBasePersenter<ViewI> {
        void getHome();
        void getBrandName(int page,int size);
    }

    //Logic业务下的Model
    interface BaseModel extends IBaseModel {
        void getHome(Callback callback);
        void getBrandName(int page,int size,Callback callback);
    }
}
