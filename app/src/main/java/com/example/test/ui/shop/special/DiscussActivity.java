package com.example.test.ui.shop.special;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.special.DiscussAdapter;
import com.example.test.base.BaseActivity;
import com.example.test.base.IBasePersenter;
import com.example.test.utils.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DiscussActivity extends BaseActivity {

    @BindView(R.id.tv_discuss_desc)
    EditText tv_Desc;
    @BindView(R.id.mRlv_sending)
    RecyclerView mRlv;
    @BindView(R.id.iv_discuss_addimg)
    ImageView iv_img;

    private DiscussAdapter pingAdapter;
    private ArrayList<String> list;

    @Override
    protected int getLayout() {
        return R.layout.activity_discuss;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected IBasePersenter createPersenter() {
        return null;
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        //网格布局
        mRlv.setLayoutManager(new GridLayoutManager(DiscussActivity.this, 3));
        pingAdapter = new DiscussAdapter(DiscussActivity.this, list);
        mRlv.setAdapter(pingAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.tv_discussdesc_cancel, R.id.tv_sending, R.id.iv_discuss_addimg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_discussdesc_cancel:
                finishAfterTransition();//关闭
                break;
            case R.id.tv_sending://发送
                initBtn();
                break;
            case R.id.iv_discuss_addimg://打开相册
                openPhoto();
                break;

        }
    }

    private void openPhoto() {
        int sum = 9 - list.size();//判断集合的长度是否大于9
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .maxSelectNum(sum)
                .imageSpanCount(9)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    private void initBtn() {
        String Desc = tv_Desc.getText().toString();
        if (TextUtils.isEmpty(Desc)) {
            Toast.makeText(this, "评论不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("text", Desc);
        bundle.putStringArrayList("list", list);
        intent.putExtra("bundle", bundle);
        setResult(200, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                // onResult Callback
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() == 0) return;
                //获取本地图片的选择地址，上传到服务器
                //头像的压缩和二次采样
                //把选中的图片插入到列表

                //进行添加图片进入集合
                for (int i = 0; i < selectList.size(); i++) {
                    String img = selectList.get(i).getPath();
                    list.add(img);
                }
                pingAdapter.notifyDataSetChanged();//刷新适配器
                if (list.size() >= 9) {//当图片放了九张
                    iv_img.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }
}