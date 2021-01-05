package com.live.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.live.R;
import com.live.app.Global;
import com.live.base.BaseActivity;
import com.live.model.bean.CreateLiveRoomBean;
import com.live.presenter.live.CreateLiveRoomPresenter;
import com.live.utils.BitmapUtils;
import com.live.utils.GlideEngine;
import com.live.utils.SpUtils;
import com.live.view.live.ICreateRoom;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

//TODO 直播
public class LiveStreamingActivity extends BaseActivity<ICreateRoom.Presenter> implements ICreateRoom.View {

    private HashMap<String, String> map;
    String isopen = "1";
    private RadioGroup mRg_create;
    private EditText name;
    private ImageView img;
    private OSS ossClient;

    String bucketName = "2002a-zwx";
    String ossPoint = "http://oss-cn-beijing.aliyuncs.com";

    String key = "LTAI4G1mVFsHAVqFkDdL6DPT";  //appkey
    String secret = "vkIBnNuNpCyIhYb2wmD4rOX2ByVx7a";  //密码
    private String name1;


    @Override
    protected int getLayout() {
        return R.layout.activity_live_streaming;
    }

    @Override
    protected void initView() {
        name = findViewById(R.id.et_room_list_name);
        mRg_create = findViewById(R.id.mRg_create);
        img = findViewById(R.id.iv_room_list_img);

        initListener();
        initOss();
        map = new HashMap<>();
    }



    private void initListener() {
        mRg_create.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.mRb_create_publicity) {
                    isopen = "1";
                }else{
                    isopen = "2";
                }
            }
        });

    }

    //初始化OSS
    private void initOss() {
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(key, secret, "");
        // 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。
        ossClient = new OSSClient(getApplicationContext(), ossPoint, credentialProvider);
    }

    @Override
    protected ICreateRoom.Presenter createPersenter() {
        return new CreateLiveRoomPresenter(this);
    }

    @Override
    protected void initData() {
        persenter=new CreateLiveRoomPresenter(this);
        name1 = this.name.getText().toString();

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPhoto();
            }
        });
    }

    @Override
    public void postCreateRoomResult(CreateLiveRoomBean createLiveRoomBean) {
        String name = createLiveRoomBean.getData().getName();
        Log.e("TAG","名字： "+name);
        Toast.makeText(this, "名字："+name, Toast.LENGTH_SHORT).show();
    }

    //TODO 打开相册
    private void openPhoto() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .loadImageEngine(GlideEngine.createGlideEngine()) // Please refer to the Demo GlideEngine.java
                .maxSelectNum(1)
                .imageSpanCount(4)
                .selectionMode(PictureConfig.MULTIPLE)
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PictureConfig.CHOOSE_REQUEST:
                List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                if (selectList.size() == 0) return;
                //获取本地图片的选择地址，上传到服务器

                //把选中的图片插入到列表
                try {
                    for (int i = 0; i < selectList.size(); i++) {
                        //头像的压缩和二次采样
                        Bitmap scaleBitmp = BitmapUtils.getScaleBitmap(selectList.get(i).getPath(), Global.HEAD_WIDTH, Global.HEAD_HEIGHT);
                        //Bitmap转uri
                        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), scaleBitmp, null, null));
                        //uri转字符串
                        String path = getRealPathFromUri(this, uri);
                        uploadHead(path);//上传头像
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);

        }
    }

    //TODO uri转字符串的方法
    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    //TODO 上传到阿里云
    private void uploadHead(String path) {
        String uid = SpUtils.getInstance().getString("uid");
        //String fileName = path.substring(path.lastIndexOf("/") + 1, path.length());
        String fileName = uid + "/" + System.currentTimeMillis() + Math.random() * 10000 + ".png";
        PutObjectRequest put = new PutObjectRequest(bucketName, fileName, path);
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                //上次进度
                Log.i("oss_upload", currentSize + "/" + totalSize);
                // 进度百分比的计算
                // int p = (int) (currentSize/totalSize*100);
                if (currentSize == totalSize) {
                    //完成
                    String headUrl = request.getUploadFilePath();
                    //
                    Log.i("HeadUrl", headUrl);
                    //request.getUploadFilePath()
                }

            }
        });
        OSSAsyncTask task = ossClient.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());
                //成功的回调中读取相关的上传文件的信息  生成一个url地址
                String url = ossClient.presignPublicObjectURL(request.getBucketName(), request.getObjectKey());
                //TODO 刷新显示到界面上
                updateHead(url);
                //调用服务器接口，把url上传到服务器的接口
                SpUtils.getInstance().setValue("img", url);

                map.put("room_name", name1);
                map.put("room_icon",url);
                map.put("isopen",isopen);
                persenter.postCreateRoom(map);
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
    }

    //TODO 上传头像
    private void updateHead(String url) {
        img.post(new Runnable() {
            @Override
            public void run() {
                Glide.with(img).load(url).apply(new RequestOptions().circleCrop()).into(img);
            }
        });
    }
}