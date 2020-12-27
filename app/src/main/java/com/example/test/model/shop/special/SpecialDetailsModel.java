package com.example.test.model.shop.special;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.model.bean.shop.special.SpecialDetailsBean;
import com.example.test.model.bean.shop.special.SpecialDetailsButtomBean;
import com.example.test.model.bean.shop.special.SpecialDetailsCommentBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.special.ISpecial;
import com.example.test.view.shop.special.ISpecialDetails;

import java.util.HashMap;

public class SpecialDetailsModel extends BaseModel implements ISpecialDetails.BaseModel {

    //专题详情数据
    @Override
    public void getSpecialDetails(int id, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getSpecialDetails(id)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<SpecialDetailsBean>(callback) {
                            @Override
                            public void onNext(SpecialDetailsBean specialDetailsBean) {
                                callback.success(specialDetailsBean);
                            }
                        })
        );
    }

    //专题详情页评论评论数据
    @Override
    public void getSpecialDetailsComment(HashMap<String, String> map, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getSpecialDetailsComment(map)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<SpecialDetailsCommentBean>(callback) {
                            @Override
                            public void onNext(SpecialDetailsCommentBean specialDetailsCommentBean) {
                                callback.success(specialDetailsCommentBean);
                            }
                        })
        );
    }

    //专题底部列表
    @Override
    public void getSpecialDetailsButtom(int id, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getSpecialDetailsButtom(id)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<SpecialDetailsButtomBean>(callback) {
                            @Override
                            public void onNext(SpecialDetailsButtomBean specialDetailsButtomBean) {
                                callback.success(specialDetailsButtomBean);
                            }
                        })
        );
    }
}
