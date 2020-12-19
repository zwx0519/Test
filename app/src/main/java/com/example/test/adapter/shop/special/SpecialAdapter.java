package com.example.test.adapter.shop.special;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.special.SpecialBean;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class SpecialAdapter extends BaseAdapter{

    public SpecialAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_special_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        SpecialBean.DataBeanX.DataBean bean= (SpecialBean.DataBeanX.DataBean) data;
        ImageView img= (ImageView) vh.getViewById(R.id.iv_special_img);
        TextView title= (TextView) vh.getViewById(R.id.tv_special_title);
        TextView subtitle= (TextView) vh.getViewById(R.id.tv_special_subtitle);
        TextView price= (TextView) vh.getViewById(R.id.tv_special_price_info);

        ImageLoaderUtils.loadImage(bean.getScene_pic_url(),img);
        TxtUtils.setTextView(title,bean.getTitle());
        TxtUtils.setTextView(subtitle,bean.getSubtitle());
        TxtUtils.setTextView(price,bean.getPrice_info()+"元起");
    }
}
