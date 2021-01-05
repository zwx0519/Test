package com.live.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.live.app.Global;
import com.live.R;
import com.live.base.BaseActivity;
import com.live.base.IBasePersenter;
import com.live.model.bean.StartLiveBean;
import com.live.presenter.live.StartLivePresenter;
import com.live.utils.SpUtils;
import com.live.view.live.IStartLive;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.TXLog;
import com.tencent.rtmp.ui.TXCloudVideoView;

public class PushActivity extends BaseActivity<IStartLive.Presenter> implements IStartLive.View, ITXLivePushListener, View.OnClickListener {

    private static String TAG = PushActivity.class.getSimpleName();

    private boolean mIsPrivateMode = false;
    private boolean mIsPushing = false;

    ImageView imgBack;
    Button btnSwitch;
    TXCloudVideoView mPusherView;
    TXLivePusher mLivePusher;
    TXLivePushConfig mLivePushConfig;

    private String mPusherURL;   // 推流地址

    @Override
    protected int getLayout() {
        return R.layout.activity_push;
    }

    //TODO 找控件
    protected void initView() {
        mPusherView = findViewById(R.id.video_push);
        imgBack = findViewById(R.id.img_back);
        btnSwitch = findViewById(R.id.btn_switch);

        if (SpUtils.getInstance().getString("token").isEmpty()) {
            Intent intent = new Intent(this, LiveStreamingActivity.class);
            Toast.makeText(this, "没有直播间，请创建", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
            initListener();
        }
    }

    @Override
    protected IStartLive.Presenter createPersenter() {
        return new StartLivePresenter(this);
    }

    @Override
    protected void initData() {
        persenter = new StartLivePresenter(this);
        persenter.postStartLive(18);
//        initiPusher();
    }

    @Override
    public void postStartLiveResult(StartLiveBean startLiveBean) {
        //保存推流地址
        mPusherURL = startLiveBean.getData().getPush_url();

        //开启直播
        initiPusher();
    }

    //开启直播
    private void initiPusher() {
        //初始化SDK推流器
        mLivePusher = new TXLivePusher(this);
        //初始化配置文件的类
        mLivePushConfig = new TXLivePushConfig();
        //设置推流器
        mLivePusher.setConfig(mLivePushConfig);
        //设置默认美颜参数，美颜样式为光滑，美颜等级5，美白等级3，红润等级2
        mLivePusher.setBeautyFilter(TXLiveConstants.BEAUTY_STYLE_SMOOTH, 5, 3, 2);
        //进行推流
        startPush();
    }

    //TODO 点击事件
    private void initListener() {
        imgBack.setOnClickListener(this);
        btnSwitch.setOnClickListener(this);
    }

    //TODO 推流
    private void startPush() {
        //推流地址
        String tRTMPURL = mPusherURL;
        // 本地预览
        mLivePusher.startCameraPreview(mPusherView);
        // 发起推流
        int ret = mLivePusher.startPusher(tRTMPURL.trim());
        if (ret == -5) {
            Log.i(TAG, "startRTMPPush:license校验失败");
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            stopPush();//关流
            finish();//关闭页面
        } else if (v.getId() == R.id.btn_switch) {
            mLivePusher.switchCamera();//切换前后相机
        }
    }

    @Override
    public void onPushEvent(int event, Bundle param) {
        String msg = param.getString(TXLiveConstants.EVT_DESCRIPTION);
        //String pushEventLog = getString(R.string.livepusher_receive_event) + event + ", " + msg;
        //TXLog.d(TAG, pushEventLog);

        // 如果开始推流，设置了隐私模式。 需要在回调里面设置，不能直接start之后直接pause
        if (event == TXLiveConstants.PUSH_EVT_PUSH_BEGIN) {
            if (mIsPrivateMode) {
                mLivePusher.pausePusher();
            }
        }
        if (event == TXLiveConstants.PUSH_ERR_NET_DISCONNECT
                || event == TXLiveConstants.PUSH_ERR_INVALID_ADDRESS
                || event == TXLiveConstants.PUSH_ERR_OPEN_CAMERA_FAIL
                || event == TXLiveConstants.PUSH_ERR_OPEN_MIC_FAIL) {
            // 遇到以上错误，则停止推流
            stopPush();
        } else if (event == TXLiveConstants.PUSH_WARNING_HW_ACCELERATION_FAIL) {
            // 开启硬件加速失败
            mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
            mLivePusher.setConfig(mLivePushConfig);
            //showToast(param.getString(TXLiveConstants.EVT_DESCRIPTION));
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_RESOLUTION) {
            TXLog.d(TAG, "change resolution to " + param.getInt(TXLiveConstants.EVT_PARAM2) + ", bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_EVT_CHANGE_BITRATE) {
            TXLog.d(TAG, "change bitrate to" + param.getInt(TXLiveConstants.EVT_PARAM1));
        } else if (event == TXLiveConstants.PUSH_EVT_OPEN_CAMERA_SUCC) {
            // 只有后置摄像头可以打开闪光灯，若默认需要开启闪光灯。 那么在打开摄像头成功后，才可以进行配置。 若果当前是前置，设定无效；若是后置，打开闪光灯。
            //turnOnFlashLight(mIsFlashLight);
        } else if (event == TXLiveConstants.PUSH_WARNING_NET_BUSY) {
            //showNetBusyTips();
        }

        //mLogInfoWindow.setLogText(null, param, event);

        // Toast错误内容
        if (event < 0) {
            //showToast(param.getString(TXLiveConstants.EVT_DESCRIPTION));
        }
    }

    @Override
    public void onNetStatus(Bundle status) {
        TXLog.d(TAG, "Current status, CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) +
                ", RES:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) +
                ", SPD:" + status.getInt(TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" +
                ", FPS:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS) +
                ", ARA:" + status.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" +
                ", VRA:" + status.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps");
        //mLogInfoWindow.setLogText(status, null, 0);
    }

    private void stopPush() {
        if (!mIsPushing) {
            return;
        }
        // 停止本地预览
        mLivePusher.stopCameraPreview(true);
        // 移除监听
        mLivePusher.setPushListener(null);
        // 停止推流
        mLivePusher.stopPusher();
        // 隐藏本地预览的View
        mPusherView.setVisibility(View.GONE);
        // 移除垫片图像
        mLivePushConfig.setPauseImg(null);
        mIsPrivateMode = false;
        mIsPushing = false;
    }

}
