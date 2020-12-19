package com.example.test.presenter.shop.home.brand;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailBean;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailListBean;
import com.example.test.model.shop.home.brand.BrandNameDetailModel;
import com.example.test.view.shop.home.brand.IBrand;

public class BrandNameDetailPresenter extends BasePresenter<IBrand.View> implements IBrand.Presenter{
    IBrand.View view;
    IBrand.Model model;

    public BrandNameDetailPresenter(IBrand.View view) {
        this.view = view;
        model=new BrandNameDetailModel();
    }

    @Override
    public void getBrand(String id) {
        if(view!=null){
            model.getBrand(id, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getBrand((BrandNameDetailBean) o);
                }
            });
        }
    }

    @Override
    public void getBrandList(String brandId) {
        if(view!=null){
            model.getBrandList(brandId, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getBrandList((BrandNameDetailListBean) o);
                }
            });
        }
    }
}
