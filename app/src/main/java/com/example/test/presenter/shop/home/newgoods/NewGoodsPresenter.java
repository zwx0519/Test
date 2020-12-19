package com.example.test.presenter.shop.home.newgoods;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsBean;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsListBean;
import com.example.test.model.shop.home.newgoods.NewGoodsModel;
import com.example.test.view.shop.home.newgoods.INewGoods;

import java.util.HashMap;

public class NewGoodsPresenter extends BasePresenter<INewGoods.View> implements INewGoods.Persenter  {
    INewGoods.Model model;
    INewGoods.View view;

    public NewGoodsPresenter(INewGoods.View view) {
        this.view = view;
        model=new NewGoodsModel();
    }

    @Override
    public void getNewGoods() {
        if(view!=null){
            model.getNewGoods(new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getNewGoodsReturn((NewGoodsBean) o);
                }
            });
        }
    }

    @Override
    public void getNewGoodsList(HashMap<String, String> map) {
        if(view!=null){
            model.getNewGoodsList(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getNewGoodsListReturn((NewGoodsListBean) o);
                }
            });
        }
    }

}
