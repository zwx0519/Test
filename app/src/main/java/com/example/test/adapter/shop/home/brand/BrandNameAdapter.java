package com.example.test.adapter.shop.home.brand;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.brand.BrandNameBean;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class BrandNameAdapter extends BaseAdapter {

    public BrandNameAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_brandname_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        BrandNameBean.DataBeanX.DataBean bean= (BrandNameBean.DataBeanX.DataBean) data;
        ImageView img= (ImageView) vh.getViewById(R.id.iv_brandname_img);
        TextView name= (TextView) vh.getViewById(R.id.tv_brandname_text);
        TextView price= (TextView) vh.getViewById(R.id.tv_brandname_price);

        ImageLoaderUtils.loadImage(bean.getApp_list_pic_url(),img);
        TxtUtils.setTextView(name,bean.getName());
        TxtUtils.setTextView(price," | "+bean.getFloor_price()+"起");
    }
}
