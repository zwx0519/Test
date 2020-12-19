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

public class TopicAdapter extends BaseAdapter {
    private Context context;
    public TopicAdapter(Context context, List Data) {
        super(context, Data);
        this.context=context;
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_topic_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        HomeBean.DataBean.TopicListBean bean= (HomeBean.DataBean.TopicListBean) data;
        TextView name= (TextView) vh.getViewById(R.id.tv_topic_name);
        TextView price= (TextView) vh.getViewById(R.id.tv_topic_price);
        TextView subtitle= (TextView) vh.getViewById(R.id.tv_topic_subtitle);
        ImageView img= (ImageView) vh.getViewById(R.id.iv_topic_img);
        Glide.with(context).load(bean.getItem_pic_url()).into(img);
        TxtUtils.setTextView(name,bean.getTitle());
        TxtUtils.setTextView(price,"￥"+bean.getPrice_info()+"元起");
        TxtUtils.setTextView(subtitle,bean.getSubtitle());
    }
}
