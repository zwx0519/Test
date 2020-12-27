package com.example.test.view.shop.special;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.model.bean.shop.special.SpecialDetailsBean;
import com.example.test.model.bean.shop.special.SpecialDetailsButtomBean;
import com.example.test.model.bean.shop.special.SpecialDetailsCommentBean;

import java.util.HashMap;

public interface ISpecialDetails {

    //Special业务下的View
    interface View extends IBaseView {
        //专题详情数据
        void getSpecialDetailsReturn(SpecialDetailsBean result);

        //专题详情页评论评论数据
        void getSpecialDetailsCommentReturn(SpecialDetailsCommentBean result);

        //专题底部列表
        void getSpecialDetailsButtomReturn(SpecialDetailsButtomBean result);
    }

    //Special业务下的Presenter
    interface Presenter extends IBasePersenter<ISpecialDetails.View> {
        //专题详情数据
        void getSpecialDetails(int id);
        //专题详情页评论评论数据
        void getSpecialDetailsComment(HashMap<String,String> map);
        //专题底部列表
        void getSpecialDetailsButtom(int id);
    }

    //Special业务下的Model
    interface BaseModel extends IBaseModel {
        //专题详情数据
        void getSpecialDetails(int id, Callback callback);
        //专题详情页评论评论数据
        void getSpecialDetailsComment(HashMap<String,String> map, Callback callback);
        //专题底部列表
        void getSpecialDetailsButtom(int id, Callback callback);
    }
}
