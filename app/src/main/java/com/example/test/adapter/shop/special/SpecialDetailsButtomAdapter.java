package com.example.test.adapter.shop.special;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.special.SpecialDetailsButtomBean;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class SpecialDetailsButtomAdapter extends BaseAdapter {

    public SpecialDetailsButtomAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_special_details_buttom_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        SpecialDetailsButtomBean.DataBean bean = (SpecialDetailsButtomBean.DataBean) data;

        ImageView img = (ImageView) vh.getViewById(R.id.iv_special_details_buttom_item_img);
        TextView title = (TextView) vh.getViewById(R.id.tv_special_details_buttom_item_title);
        ImageLoaderUtils.loadImage(bean.getScene_pic_url(),img);
        TxtUtils.setTextView(title,bean.getTitle());
    }
}
