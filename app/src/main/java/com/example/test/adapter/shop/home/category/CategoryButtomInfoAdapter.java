package com.example.test.adapter.shop.home.category;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.category.CategoryBottomInfoBean;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class CategoryButtomInfoAdapter extends BaseAdapter {

    public CategoryButtomInfoAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_category_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        CategoryBottomInfoBean.DataBean.GoodsListBean bean= (CategoryBottomInfoBean.DataBean.GoodsListBean) data;

        ImageView image = (ImageView) vh.getViewById(R.id.iv_category_img);
        TextView name = (TextView) vh.getViewById(R.id.tv_category_name);
        TextView floor_price = (TextView) vh.getViewById(R.id.tv_category_price);

        ImageLoaderUtils.loadImage(bean.getList_pic_url(),image);
        TxtUtils.setTextView(name,bean.getName());
        floor_price.setText("￥"+bean.getRetail_price());
    }
}
