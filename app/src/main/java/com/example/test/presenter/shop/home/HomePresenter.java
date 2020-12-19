package com.example.test.presenter.shop.home;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.home.brand.BrandNameBean;
import com.example.test.model.shop.home.HomeModel;
import com.example.test.view.shop.home.IHome;

public class HomePresenter extends BasePresenter<IHome.ViewI> implements IHome.Presenter {
    IHome.BaseModel model;
    IHome.ViewI mView;

    public HomePresenter(IHome.ViewI mView) {
        this.mView = mView;
        model=new HomeModel();
    }

    @Override
    public void getHome() {
        if(mView!=null){
            model.getHome(new Callback() {
                @Override
                public void fail(String msg) {
                    mView.tips(msg);
                }

                @Override
                public void success(Object o) {
                    mView.getHomeReturn((HomeBean) o);
                }
            });
        }
    }

    @Override
    public void getBrandName(int page, int size) {
        if(mView!=null){
            model.getBrandName(page, size, new Callback() {
                @Override
                public void fail(String msg) {
                    mView.tips(msg);
                }

                @Override
                public void success(Object o) {
                    mView.getBrandNameReturn((BrandNameBean) o);
                }
            });
        }
    }
}
