package com.example.test.adapter.shop.home.newgoods;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class NewGoodsAdapter extends BaseAdapter {

    private Context context;
    public NewGoodsAdapter(Context context, List Data) {
        super(context, Data);
        this.context=context;
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_newgoods_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        HomeBean.DataBean.NewGoodsListBean bean= (HomeBean.DataBean.NewGoodsListBean) data;
        TextView name= (TextView) vh.getViewById(R.id.tv_newgoods_name);
        TextView price= (TextView) vh.getViewById(R.id.tv_newgoods_retail_price);
        ImageView img= (ImageView) vh.getViewById(R.id.iv_newgoods_img);
        Glide.with(context).load(bean.getList_pic_url()).into(img);
        TxtUtils.setTextView(name,bean.getName());
        TxtUtils.setTextView(price,"￥"+bean.getRetail_price());
    }
}
