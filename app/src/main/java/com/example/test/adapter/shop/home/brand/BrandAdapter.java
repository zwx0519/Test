package com.example.test.adapter.shop.home.brand;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class BrandAdapter extends BaseAdapter {

    private Context context;
    public BrandAdapter(Context context, List Data) {
        super(context, Data);
        this.context=context;
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_brand_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        HomeBean.DataBean.BrandListBean bean= (HomeBean.DataBean.BrandListBean) data;

        ImageView img= (ImageView) vh.getViewById(R.id.iv_brand_img);
        TextView price= (TextView) vh.getViewById(R.id.tv_brand_floor_price);
        TextView name= (TextView) vh.getViewById(R.id.tv_brand_name);
        Glide.with(context).load(bean.getNew_pic_url()).into(img);
        TxtUtils.setTextView(price,bean.getFloor_price()+"元起");
        TxtUtils.setTextView(name,bean.getName());
    }
}
