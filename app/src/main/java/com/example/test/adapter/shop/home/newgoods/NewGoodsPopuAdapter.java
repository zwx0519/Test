package com.example.test.adapter.shop.home.newgoods;

import android.content.Context;
import android.widget.Button;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.newgoods.NewGoodsListBean;

import java.util.List;

public class NewGoodsPopuAdapter extends BaseAdapter {

    public NewGoodsPopuAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_newgoods_popw_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        NewGoodsListBean.DataBeanX.FilterCategoryBean bean= (NewGoodsListBean.DataBeanX.FilterCategoryBean) data;
        Button type = (Button) vh.getViewById(R.id.btn_newgoods_popw_type);
        type.setText(bean.getName());
    }
}
