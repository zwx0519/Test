package com.live.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.live.R;
import com.live.base.BaseAdapter;
import com.live.model.bean.RoomListBean;
import com.live.utils.ImageLoaderUtils;

import java.util.List;

public class RoomListAdapter extends BaseAdapter {

    public RoomListAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_room_list_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        RoomListBean.DataBean bean = (RoomListBean.DataBean) data;

        TextView name= (TextView) vh.getViewById(R.id.tv_room_list_name);
        TextView owner= (TextView) vh.getViewById(R.id.tv_room_list_owner);
        ImageView img= (ImageView) vh.getViewById(R.id.iv_room_list_img);

        name.setText(bean.getName());
        owner.setText(bean.getId()+"");

    }
}
