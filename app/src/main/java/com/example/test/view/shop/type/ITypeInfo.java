package com.example.test.view.shop.type;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.view.shop.special.ISpecial;

public interface ITypeInfo {
    //TypeInfo业务下的View
    interface View extends IBaseView {
        void getSpecialReturn(SpecialBean result);
    }
    //TypeInfo业务下的Presenter
    interface Presenter extends IBasePersenter<ISpecial.View> {
        void getSpecial(int page);
    }

    //TypeInfo业务下的Model
    interface BaseModel extends IBaseModel {
        void getSpecial(int page, Callback callback);
    }
}
