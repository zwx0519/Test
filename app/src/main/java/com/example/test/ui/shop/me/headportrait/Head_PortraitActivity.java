package com.example.test.ui.shop.me.headportrait;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseActivity;
import com.example.test.base.IBasePersenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Head_PortraitActivity extends BaseActivity {

    @BindView(R.id.include_head_portrait_one)
    RelativeLayout inclue_one;
    @BindView(R.id.include_head_portrait_two)
    RelativeLayout inclue_two;
    @BindView(R.id.include_head_portrait_three)
    RelativeLayout inclue_three;
    @BindView(R.id.include_head_portrait_four)
    RelativeLayout inclue_four;
    @BindView(R.id.iv_head_portrait_pic)
    ImageView iv_Pic;
    @BindView(R.id.iv_head_portrait_select_img)
    ImageView iv_Img;

    @Override
    protected int getLayout() {
        return R.layout.activity_head_portrait;
    }

    @Override
    protected void initView() {
        TextView one_left = inclue_one.findViewById(R.id.tv_head_portrait_left);
        TextView two_left = inclue_two.findViewById(R.id.tv_head_portrait_left);
        TextView three_left = inclue_three.findViewById(R.id.tv_head_portrait_left);
        TextView four_left = inclue_four.findViewById(R.id.tv_head_portrait_left);

        one_left.setText("昵称");
        two_left.setText("微信号");
        three_left.setText("拍一拍");
        four_left.setText("更多");
    }

    @Override
    protected IBasePersenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_head_portrait_pic, R.id.iv_head_portrait_select_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head_portrait_pic:

                break;
            case R.id.iv_head_portrait_select_img:
                openPhoto();
                break;
        }
    }



    //TODO 打开相册
    private void openPhoto() {
//        PictureSelector.create(this)
//                .openGallery(PictureMimeType.ofImage())
//                .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
//                .maxSelectNum(1)
//                .imageSpanCount(4)
//                .selectionMode(PictureConfig.MULTIPLE)
//                .forResult(PictureConfig.CHOOSE_REQUEST);
    }
}