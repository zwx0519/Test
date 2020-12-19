package com.example.test.adapter.shop.home;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class HotGoodsAdapter extends BaseAdapter {

    private Context context;
    public HotGoodsAdapter(Context context, List Data) {
        super(context, Data);
        this.context=context;
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_hotgoods_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        HomeBean.DataBean.HotGoodsListBean bean= (HomeBean.DataBean.HotGoodsListBean) data;

        ImageView img= (ImageView) vh.getViewById(R.id.iv_hotgoods_img);
        TextView name= (TextView) vh.getViewById(R.id.tv_hotgoods_name);
        TextView brief= (TextView) vh.getViewById(R.id.tv_hotgoods_goods_brief);
        TextView price= (TextView) vh.getViewById(R.id.tv_hotgoods_retail_price);

        Glide.with(context).load(bean.getList_pic_url()).into(img);
        TxtUtils.setTextView(name,bean.getName());
        TxtUtils.setTextView(brief,bean.getGoods_brief());
        TxtUtils.setTextView(price,"￥"+bean.getRetail_price());
    }
}
