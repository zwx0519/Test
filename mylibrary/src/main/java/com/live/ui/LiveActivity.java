package com.live.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.live.app.Global;
import com.live.R;
import com.live.base.BaseActivity;
import com.live.model.bean.CreateLiveRoomBean;
import com.live.model.bean.RoomLiveUrlBean;
import com.live.presenter.live.RoomLiveUrlPresenter;
import com.live.utils.SpUtils;
import com.live.view.live.ICreateRoom;
import com.live.view.live.IRoomLiveUrl;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class LiveActivity extends BaseActivity<IRoomLiveUrl.Presenter> implements IRoomLiveUrl.View,ITXLivePlayListener, View.OnClickListener {

    private static String TAG = LiveActivity.class.getSimpleName();
    private static final int CODE_LIVE = 100;

    ImageView imgBack;

    TXLivePlayer mLivePlayer; //直播拉流的视频播放器
    TXLivePlayConfig mPlayerConfig; //播放器的配置类
    TXCloudVideoView mVideoView; //播放器view

    private String mPlayUrl;//拉流地址
    private boolean mIsPlaying;

    @Override
    protected int getLayout() {
        return R.layout.activity_live;
    }

    protected void initView() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        imgBack = findViewById(R.id.img_back);
        mVideoView = findViewById(R.id.video_play);
        mPlayerConfig = new TXLivePlayConfig();

        initListener();
    }

    @Override
    protected IRoomLiveUrl.Presenter createPersenter() {
        return new RoomLiveUrlPresenter(this);
    }

    @Override
    protected void initData() {
        //调用开启直播接口
        persenter=new RoomLiveUrlPresenter(this);

        Intent intent = getIntent();
        int id = intent.getIntExtra("roomid", 0);
        HashMap<String, String> map = new HashMap<>();
        map.put("roomid",String.valueOf(id));
        //如过token不为空
        if(!TextUtils.isEmpty( SpUtils.getInstance().getString( "token" ) )){
            if(map.size()!=0){
                Toast.makeText( this, id+"", Toast.LENGTH_SHORT ).show();
                persenter.postRoomLiveUrl(map);

            }else{
                Toast.makeText(this, "没有传入正确的id值", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText( this, "您没有登录", Toast.LENGTH_SHORT ).show();
        }

    }

    //TODO 拉取流
    @Override
    public void postRoomLiveUrlResult(RoomLiveUrlBean roomLiveUrlBean) {
        RoomLiveUrlBean.DataBean data = roomLiveUrlBean.getData();
        //获取拉流地址
        mPlayUrl = data.getPlay_url();
        startPlay();//打开直播
        Log.e(TAG, "zwx-url: "+data.getPlay_url() );

    }

    //TODO 监听事件
    private void initListener(){
        imgBack.setOnClickListener(this);

        mLivePlayer = new TXLivePlayer(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.img_back){
            stopPlay();//关流
            finish();
        }
    }

    //TODO 开启直播
    private void startPlay(){
        //开启直播视频播放器
        mLivePlayer.setPlayerView(mVideoView);
        mLivePlayer.setPlayListener(this);
        mLivePlayer.enableHardwareDecode(false);
        mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        mPlayerConfig.setEnableMessage(true);
        mLivePlayer.setConfig(mPlayerConfig);
        //在常量类里面定死了的拉流地址
        //int code = mLivePlayer.startPlay(Global.PLAY_URL,TXLivePlayer.PLAY_TYPE_LIVE_RTMP);

        //点击条目时的拉流
        int code = mLivePlayer.startPlay(mPlayUrl,TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
        mIsPlaying = code == 0;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onPlayEvent(int event, Bundle param) {
        Log.d(TAG, "receive event: " + event + ", " + param.getString(TXLiveConstants.EVT_DESCRIPTION));
        switch (event) {
            case TXLiveConstants.PLAY_EVT_PLAY_BEGIN:
                //Log.d("AutoMonitor", "PlayFirstRender,cost=" + (System.currentTimeMillis() - mStartPlayTS));
            case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME:
                //stopLoadingAnimation();
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_LOADING:
                //startLoadingAnimation();
                break;
            case TXLiveConstants.PLAY_EVT_CHANGE_RESOLUTION:
                Log.d(TAG, "size " + param.getInt(TXLiveConstants.EVT_PARAM1) + "x" + param.getInt(TXLiveConstants.EVT_PARAM2));
                break;
            case TXLiveConstants.PLAY_EVT_GET_MESSAGE:
                byte[] data = param.getByteArray(TXLiveConstants.EVT_GET_MSG);
                String seiMessage = "";
                if (data != null && data.length > 0) {
                    try {
                        seiMessage = new String(data, StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Toast.makeText(getApplicationContext(), seiMessage, Toast.LENGTH_SHORT).show();
                break;
            case TXLiveConstants.PLAY_EVT_CHANGE_ROTATION:
                break;
            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT:
            case TXLiveConstants.PLAY_EVT_PLAY_END:
                stopPlay();
                break;
        }
        if (event < 0) {
            Toast.makeText(this, param.getString(TXLiveConstants.EVT_DESCRIPTION), Toast.LENGTH_SHORT).show();
        }
    }

    //TODO 日志
    @Override
    public void onNetStatus(Bundle bundle) {
        Log.d(TAG, "Current status, CPU:" + bundle.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                ", RES:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                ", SPD:" + bundle.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                ", FPS:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                ", ARA:" + bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                ", VRA:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
    }

    //TODO 停止直播
    private void stopPlay() {
        if (!mIsPlaying) {
            return;
        }
        if (mLivePlayer != null) {
            mLivePlayer.stopRecord();
            mLivePlayer.setPlayListener(null);
            mLivePlayer.stopPlay(true);
        }
        mIsPlaying = false;
        //处理UI相关操作
    }

}
