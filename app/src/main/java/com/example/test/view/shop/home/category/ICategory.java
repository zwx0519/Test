package com.example.test.view.shop.home.category;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.category.CategoryBean;
import com.example.test.model.bean.shop.home.category.CategoryBottomInfoBean;
import com.example.test.model.bean.shop.shoppingcar.AddShoppingCarBean;

import java.util.HashMap;
import java.util.Map;

public interface ICategory {

    interface View extends IBaseView {
        //定义一个被实现的View层接口方法
        void getCategoryReturn(CategoryBean result);

        void getCategoryBottomInfoReturn(CategoryBottomInfoBean result);


    }

    interface Persenter extends IBasePersenter<View> {
        //定义一个V层调用的接口
        void getCategory(String id);

        void getCategoryBottomInfo(String id);


    }

    interface Model extends IBaseModel {
        //定义一个加载数据的接口方法   被P层
        void getCategory(String id, Callback callback);

        void getCategoryBottomInfo(String id, Callback callback);


    }
}
