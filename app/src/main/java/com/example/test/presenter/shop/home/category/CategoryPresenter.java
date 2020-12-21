package com.example.test.presenter.shop.home.category;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.category.CategoryBean;
import com.example.test.model.bean.shop.home.category.CategoryBottomInfoBean;
import com.example.test.model.shop.home.category.CategoryModel;
import com.example.test.view.shop.home.category.ICategory;

public class CategoryPresenter extends BasePresenter<ICategory.View> implements ICategory.Persenter{
    ICategory.View view;
    ICategory.Model model;

    public CategoryPresenter(ICategory.View view) {
        this.view = view;
        model=new CategoryModel();
    }

    //居家 商品详情购买页
    @Override
    public void getCategory(String id) {
        if(view!=null){
            model.getCategory(id, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getCategoryReturn((CategoryBean) o);
                }
            });
        }
    }

    //商品 详情购买页 底部数据列表
    @Override
    public void getCategoryBottomInfo(String id) {
        if(view!=null){
            model.getCategoryBottomInfo(id, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                   view.getCategoryBottomInfoReturn((CategoryBottomInfoBean) o);
                }
            });
        }
    }
}
