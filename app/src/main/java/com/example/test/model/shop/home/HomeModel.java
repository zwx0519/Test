package com.example.test.model.shop.home;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.home.brand.BrandNameBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.home.IHome;

public class HomeModel extends BaseModel implements IHome.BaseModel{

    @Override
    public void getHome(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getHome()
                .compose(RxUtils.rxScheduler())//线程切换
                .subscribeWith(new CommonSubscriber<HomeBean>(callback) {
                    @Override
                    public void onNext(HomeBean homeBean) {
                        callback.success(homeBean);
                    }
                })
        );
    }

    @Override
    public void getBrandName(int page, int size, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getBrandName(page,size)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<BrandNameBean>(callback) {
                    @Override
                    public void onNext(BrandNameBean brandNameBean) {
                        callback.success(brandNameBean);
                    }
                })
        );
    }
}
