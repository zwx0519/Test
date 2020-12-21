package com.example.test.view.shop.type;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.type.TypeBean;
import com.example.test.model.bean.shop.type.TypeInfBean;

public interface IType {
    //Type业务下的View
    interface View extends IBaseView {
        void getTypeReturn(TypeBean result);
        void getTypeInfoReturn(TypeInfBean result);
    }
    //Type业务下的Presenter
    interface Presenter extends IBasePersenter<View> {
        void getType();
        void getTypeInfo(String id);
    }

    //Type业务下的Model
    interface BaseModel extends IBaseModel {
        void getType(Callback callback);
        void getTypeInfo(String id, Callback callback);
    }
}
