package com.example.test.view.shop.special;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.view.shop.home.IHome;

public interface ISpecial {

    //Special业务下的View
    interface View extends IBaseView {
        void getSpecialReturn(SpecialBean result);
    }
    //Special业务下的Presenter
    interface Presenter extends IBasePersenter<ISpecial.View> {
        void getSpecial(int page);
    }

    //Special业务下的Model
    interface BaseModel extends IBaseModel {
        void getSpecial(int page,Callback callback);
    }
}
