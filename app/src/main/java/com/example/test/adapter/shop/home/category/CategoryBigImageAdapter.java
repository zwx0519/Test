package com.example.test.adapter.shop.home.category;

import android.content.Context;
import android.widget.ImageView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.utils.ImageLoaderUtils;

import java.util.List;

public class CategoryBigImageAdapter extends BaseAdapter {

    public CategoryBigImageAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_category_bigimage_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        String bean = (String) data;

        ImageView img= (ImageView) vh.getViewById(R.id.iv_bigimage_img);
        ImageLoaderUtils.loadImage(bean,img);

    }
}
