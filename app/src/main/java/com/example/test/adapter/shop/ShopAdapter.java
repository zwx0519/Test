package com.example.test.adapter.shop;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class ShopAdapter extends PagerAdapter {

    private Activity activity;
    private List<Integer> list;
    private PopupWindow window;
    private Disposable subscribe;

    public ShopAdapter(Activity activity, List<Integer> list, PopupWindow window) {
        this.activity = activity;
        this.list = list;
        this.window = window;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView view = new ImageView(activity);
        view.setScaleType((ImageView.ScaleType.FIT_XY));
        Glide.with(activity).load(list.get(position)).into(view);
        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
