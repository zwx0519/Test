package com.live.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.live.R;
import com.live.adapter.RoomListAdapter;
import com.live.base.BaseActivity;
import com.live.base.BaseAdapter;
import com.live.model.bean.RoomListBean;
import com.live.model.bean.RoomLiveUrlBean;
import com.live.presenter.live.RoomListPresenter;
import com.live.view.live.IRoomList;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends BaseActivity<IRoomList.Presenter> implements IRoomList.View, View.OnClickListener {

    RecyclerView mRlv;//条目
    ImageView imgBack;//返回
    ImageView imgStartLive;//开启直播
    TextView update;
    private Button jump;

    @Override
    protected int getLayout() {
        return R.layout.activity_room;
    }

    protected void initView() {
        mRlv = findViewById(R.id.mRlv_room_list);
        imgBack = findViewById(R.id.img_back);
        imgStartLive = findViewById(R.id.img_startLive);
        jump = findViewById(R.id.btn_room_list_jump);
        update = findViewById(R.id.tv_room_update);
        //初始化点击事件
        initListener();
    }

    @Override
    protected IRoomList.Presenter createPersenter() {
        return new RoomListPresenter(this);
    }

    @Override
    protected void initData() {
        persenter = new RoomListPresenter(this);
        persenter.getRoomList();
    }

    private void initListener() {
        jump.setOnClickListener(this);
        imgStartLive.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.img_startLive) {//开启直播
            Intent intent = new Intent(RoomActivity.this, PushActivity.class);
            startActivity(intent);
        } else if (id == R.id.img_back) {//返回
            finish();
        } else if (id == R.id.btn_room_list_jump) {
            //创建自己的直播间
            Intent intent = new Intent(RoomActivity.this, LiveStreamingActivity.class);
            startActivity(intent);
        }else if(id==R.id.tv_room_update){
            Intent intent = new Intent(RoomActivity.this, UpdateLiveRoomActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void getRoomListResult(RoomListBean roomListBean) {
        List<RoomListBean.DataBean> data = roomListBean.getData();

        mRlv.setLayoutManager(new LinearLayoutManager(this));
        RoomListAdapter roomListAdapter = new RoomListAdapter(this, data);
        mRlv.setAdapter(roomListAdapter);

        //点击条目跳转进入直播
        roomListAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                Intent intent = new Intent(RoomActivity.this, LiveActivity.class);
                int isopen = data.get(pos).getIsopen();
                if (isopen == 1) {  //开放的房间
                    intent.putExtra("roomid", data.get(pos).getId());
                    startActivity(intent);
                } else if (isopen == 2) {  //密码
                    Toast.makeText(RoomActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
