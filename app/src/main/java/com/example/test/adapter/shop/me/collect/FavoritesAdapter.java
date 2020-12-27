package com.example.test.adapter.shop.me.collect;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.me.collect.Favorites;
import com.example.test.utils.ImageLoaderUtils;

import java.util.List;

public class FavoritesAdapter extends BaseAdapter<Favorites> {

    public FavoritesAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_favorites_item;
    }

    @Override
    protected void bindData(Favorites data, VH vh) {
        TextView tvfavoritesname = (TextView) vh.getViewById(R.id.tv_favorites_name);
        TextView tvfavoritestitle = (TextView) vh.getViewById(R.id.tv_favorites_title);
        TextView tvfavoritesprice = (TextView) vh.getViewById(R.id.tv_favorites_price);
        ImageView ivfavoritespic = (ImageView) vh.getViewById(R.id.iv_favorites_pic);

        tvfavoritesname.setText(data.getName());
        tvfavoritesprice.setText("￥"+data.getPrice());
        tvfavoritestitle.setText(data.getTitle());
        ImageLoaderUtils.loadImage(data.getPic(),ivfavoritespic);
    }

}
