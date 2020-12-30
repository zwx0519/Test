package com.example.test.adapter.shop.special;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.utils.ImageLoaderUtils;

import java.util.List;

public class DiscussAdapter extends BaseAdapter {

    public DiscussAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_discuss_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        String path = (String) data;
        ImageView img_pic = (ImageView) vh.getViewById(R.id.img_pic);
        ImageLoaderUtils.loadImage(path,img_pic);
    }
}
