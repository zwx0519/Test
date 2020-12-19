package com.example.test.ui.shop.home.brand;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.utils.ImageLoaderUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
//TODO 品种制造商点击图片的详情
public class BrandActivity extends AppCompatActivity {

    @BindView(R.id.iv_brand_pic)
    ImageView ivBrandPic;
    @BindView(R.id.tv_brand_name)
    TextView tvBrandName;
    @BindView(R.id.tv_brand_simple)
    TextView tvBrandSimple;
    private Unbinder bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);
        bind = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String simple = intent.getStringExtra("simple");
        String pic = intent.getStringExtra("pic");

        tvBrandName.setText(name);
        tvBrandSimple.setText(simple);
        ImageLoaderUtils.loadImage(pic,ivBrandPic);
        //Glide.with(this).load(pic).into(ivBrandPic);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}