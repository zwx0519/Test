package com.example.test.presenter.shop.special;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.model.shop.special.SpecialModel;
import com.example.test.view.shop.home.IHome;
import com.example.test.view.shop.special.ISpecial;

public class SpecialPresenter extends BasePresenter<ISpecial.View> implements ISpecial.Presenter {

    ISpecial.View view;
    ISpecial.BaseModel model;

    public SpecialPresenter(ISpecial.View view) {
        this.view = view;
        this.model=new SpecialModel();
    }

    @Override
    public void getSpecial(int page) {
        if(view!=null){
            this.model.getSpecial(page,new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getSpecialReturn((SpecialBean) o);
                }
            });
        }
    }
}
