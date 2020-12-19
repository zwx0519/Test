package com.example.test.model.shop.home.brand;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailBean;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailListBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.home.brand.IBrand;

public class BrandNameDetailModel extends BaseModel implements IBrand.Model {

    @Override
    public void getBrand(String id, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getBrandNameDetail(id)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<BrandNameDetailBean>(callback) {
                    @Override
                    public void onNext(BrandNameDetailBean brandNameDetailBean) {
                        callback.success(brandNameDetailBean);
                    }
                })
        );
    }

    @Override
    public void getBrandList(String brandId, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getBrandNameDetailList(brandId)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<BrandNameDetailListBean>(callback) {
                    @Override
                    public void onNext(BrandNameDetailListBean brandNameDetailListBean) {
                        callback.success(brandNameDetailListBean);
                    }
                })
        );
    }
}
