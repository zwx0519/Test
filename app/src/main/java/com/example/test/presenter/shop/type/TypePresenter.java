package com.example.test.presenter.shop.type;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.type.TypeBean;
import com.example.test.model.bean.shop.type.TypeInfBean;
import com.example.test.model.shop.type.TypeModel;
import com.example.test.view.shop.type.IType;

public class TypePresenter extends BasePresenter<IType.View> implements IType.Presenter{
    IType.View view;
    IType.BaseModel model;

    public TypePresenter(IType.View view) {
        this.view = view;
        model=new TypeModel();
    }

    @Override
    public void getType() {
        if(view!=null){
            this.model.getType(new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getTypeReturn((TypeBean) o);
                }
            });
        }
    }

    @Override
    public void getTypeInfo(String id) {
        if(view!=null){
            this.model.getTypeInfo(id,new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getTypeInfoReturn((TypeInfBean) o);
                }
            });
        }
    }
}
