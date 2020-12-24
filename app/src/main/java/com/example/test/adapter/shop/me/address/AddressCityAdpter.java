package com.example.test.adapter.shop.me.address;

import android.content.Context;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.me.address.AddressCityBean;

import java.util.List;

public class AddressCityAdpter extends BaseAdapter {

    public AddressCityAdpter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_address_add_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        AddressCityBean.DataBean bean= (AddressCityBean.DataBean) data;
        TextView province= (TextView) vh.getViewById(R.id.tv_address_item_province);
        province.setText(bean.getName());
    }
}
