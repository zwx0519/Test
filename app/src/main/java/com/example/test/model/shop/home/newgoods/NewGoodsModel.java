package com.example.test.model.shop.home.newgoods;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsBean;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsListBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.home.newgoods.INewGoods;

import java.util.HashMap;

public class NewGoodsModel extends BaseModel implements INewGoods.Model{

    @Override
    public void getNewGoods(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getNewGoods()
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<NewGoodsBean>(callback) {
                    @Override
                    public void onNext(NewGoodsBean newGoodsBean) {
                        callback.success(newGoodsBean);
                    }
                })
        );
    }

    @Override
    public void getNewGoodsList(HashMap<String, String> map, Callback callback) {
        addDisposable(
                new HttpManager().getService().getNewGoodsList(map)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<NewGoodsListBean>(callback) {
                    @Override
                    public void onNext(NewGoodsListBean newGoodsListBean) {
                        callback.success(newGoodsListBean);
                    }
                })
        );
    }

}
