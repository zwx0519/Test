package com.example.test.presenter.shop.special;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.model.bean.shop.special.SpecialDetailsBean;
import com.example.test.model.bean.shop.special.SpecialDetailsButtomBean;
import com.example.test.model.bean.shop.special.SpecialDetailsCommentBean;
import com.example.test.model.shop.special.SpecialDetailsModel;
import com.example.test.view.shop.special.ISpecialDetails;

import java.util.HashMap;

public class SpecialDetailsPresenter extends BasePresenter<ISpecialDetails.View> implements ISpecialDetails.Presenter {

    ISpecialDetails.View view;
    ISpecialDetails.BaseModel model;

    public SpecialDetailsPresenter(ISpecialDetails.View view) {
        this.view = view;
        model = new SpecialDetailsModel();
    }

    //专题详情数据
    @Override
    public void getSpecialDetails(int id) {
        if (view != null) {
            this.model.getSpecialDetails(id, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getSpecialDetailsReturn((SpecialDetailsBean) o);
                }
            });
        }
    }

    //专题详情页评论评论数据
    @Override
    public void getSpecialDetailsComment(HashMap<String, String> map) {
        if (view != null) {
            this.model.getSpecialDetailsComment(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getSpecialDetailsCommentReturn((SpecialDetailsCommentBean) o);
                }
            });
        }
    }

    //专题底部列表
    @Override
    public void getSpecialDetailsButtom(int id) {
        if (view != null) {
            this.model.getSpecialDetailsButtom(id, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getSpecialDetailsButtomReturn((SpecialDetailsButtomBean) o);
                }
            });
        }
    }

}
