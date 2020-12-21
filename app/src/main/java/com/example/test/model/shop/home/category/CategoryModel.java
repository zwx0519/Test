package com.example.test.model.shop.home.category;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.category.CategoryBean;
import com.example.test.model.bean.shop.home.category.CategoryBottomInfoBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.home.category.ICategory;

public class CategoryModel extends BaseModel implements ICategory.Model {

    //居家 商品详情购买页
    @Override
    public void getCategory(String id, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getCategory(id)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<CategoryBean>(callback) {
                    @Override
                    public void onNext(CategoryBean categoryBean) {
                        callback.success(categoryBean);
                    }
                })
        );
    }

     //商品 详情购买页 底部数据列表
    @Override
    public void getCategoryBottomInfo(String id, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getCategoryBottomInfo(id)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<CategoryBottomInfoBean>(callback) {
                            @Override
                            public void onNext(CategoryBottomInfoBean categoryBottomInfoBean) {
                                callback.success(categoryBottomInfoBean);
                            }
                        })
        );
    }

}
