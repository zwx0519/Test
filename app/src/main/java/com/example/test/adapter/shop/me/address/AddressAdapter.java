package com.example.test.adapter.shop.me.address;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.utils.ImageLoaderUtils;

import java.util.List;

public class AddressAdapter extends BaseAdapter {

    public AddressAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_address_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        ShoppingCarBean.DataBean.CartListBean bean= (ShoppingCarBean.DataBean.CartListBean) data;

        ImageView img = (ImageView) vh.getViewById(R.id.iv_address_item_img);
        TextView Name = (TextView) vh.getViewById(R.id.tv_address_item_name);
        TextView Price = (TextView) vh.getViewById(R.id.tv_address_item_price);
        TextView Number = (TextView) vh.getViewById(R.id.tv_address_item_count);

        ImageLoaderUtils.loadImage(bean.getList_pic_url(),img);
        Price.setText("￥"+bean.getRetail_price());
        Name.setText(bean.getGoods_name());
        Number.setText(String.valueOf(bean.getNumber()));
    }
}
