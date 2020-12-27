package com.example.test.adapter.shop.special;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.utils.ImageLoaderUtils;

import java.util.List;

public class SpecialDetailsAdapter extends BaseAdapter {

    public SpecialDetailsAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_special_details_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        String str = (String) data;
        ImageView img = (ImageView) vh.getViewById(R.id.iv_special_details_item_img);
        ImageLoaderUtils.loadImage(str,img);
    }
}
