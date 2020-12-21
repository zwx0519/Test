package com.example.test.adapter.shop.home.category;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private Context context;
    public CategoryAdapter(Context context, List Data) {
        super(context, Data);
        this.context=context;
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_category_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        HomeBean.DataBean.CategoryListBean.GoodsListBean bean= (HomeBean.DataBean.CategoryListBean.GoodsListBean) data;
        ImageView image = (ImageView) vh.getViewById(R.id.iv_category_img);
        TextView name = (TextView) vh.getViewById(R.id.tv_category_name);
        TextView floor_price = (TextView) vh.getViewById(R.id.tv_category_price);


        Glide.with(context).load(bean.getList_pic_url()).into(image);
        TxtUtils.setTextView(name,bean.getName());
        floor_price.setText("￥"+bean.getRetail_price());
    }
}
