package com.example.test.adapter.shop.me.address;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.me.address.User;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class AddressAddAdapter extends BaseAdapter {

    public AddressAddAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_address_add_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        User users = (User) data;
        TextView name= (TextView) vh.getViewById(R.id.tv_address_add_list_name);
        TextView more= (TextView) vh.getViewById(R.id.tv_address_add_list_more);
        TextView phone= (TextView) vh.getViewById(R.id.tv_address_add_list_phone);
        TextView address= (TextView) vh.getViewById(R.id.tv_order_form_list_address);

        TxtUtils.setTextView(name,users.getMName());
        TxtUtils.setTextView(phone,users.getMPhone());
        TxtUtils.setTextView(address,users.getMCity()+users.getMDetail());

        if(users.getABoolean()){
            more.setVisibility(View.VISIBLE);
        }else {
            more.setVisibility(View.GONE);
        }
    }
}
