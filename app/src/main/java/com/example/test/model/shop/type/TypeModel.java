package com.example.test.model.shop.type;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.type.TypeBean;
import com.example.test.model.bean.shop.type.TypeInfBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.type.IType;

public class TypeModel extends BaseModel implements IType.BaseModel{

    //分类
    @Override
    public void getType(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getHomeType()
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<TypeBean>(callback) {
                            @Override
                            public void onNext(TypeBean typeBean) {
                                callback.success(typeBean);
                            }
                        })
        );
    }

    //分类数据
    @Override
    public void getTypeInfo(String id, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getTypeInfo(id)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<TypeInfBean>(callback) {
                            @Override
                            public void onNext(TypeInfBean typeInfBean) {
                                callback.success(typeInfBean);
                            }
                        })
        );
    }
}
