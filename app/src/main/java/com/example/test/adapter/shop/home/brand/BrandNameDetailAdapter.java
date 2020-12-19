package com.example.test.adapter.shop.home.brand;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.brand.BrandNameDetailListBean;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class BrandNameDetailAdapter extends BaseAdapter {

    public BrandNameDetailAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_brand_name_detail_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        BrandNameDetailListBean.DataBeanX.DataBean bean= (BrandNameDetailListBean.DataBeanX.DataBean) data;
        ImageView img= (ImageView) vh.getViewById(R.id.iv_brand_name_detail_list_img);
        TextView name= (TextView) vh.getViewById(R.id.tv_brand_name_detail_list_name);
        TextView price= (TextView) vh.getViewById(R.id.tv_brand_name_detail_list_price);

        ImageLoaderUtils.loadImage(bean.getList_pic_url(),img);
        TxtUtils.setTextView(name,bean.getName());
        TxtUtils.setTextView(price,bean.getRetail_price());
    }
}
