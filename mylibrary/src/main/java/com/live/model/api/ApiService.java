package com.live.model.api;


import com.live.model.bean.CreateLiveRoomBean;
import com.live.model.bean.RoomListBean;
import com.live.model.bean.RoomLiveUrlBean;
import com.live.model.bean.StartLiveBean;
import com.live.model.bean.UpdateLiveRoomBean;

import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    String BASE_URL = "https://cdplay.cn/";

    //创建直播房间
    @POST("api/live/createRoom")
    @FormUrlEncoded
    Flowable<CreateLiveRoomBean> postCreateRoom(@FieldMap HashMap<String, String> map);

    //获取房间信息
    @GET("api/live/getRoomList")
    Flowable<RoomListBean> getRoomList();

    //房间播放地址获取
    @POST("api/live/roomLiveUrl")
    @FormUrlEncoded
    Flowable<RoomLiveUrlBean> postRoomLiveUrl(@FieldMap HashMap<String, String> map);

   //开启直播
    @POST("api/live/startLive")
    @FormUrlEncoded
    Flowable<StartLiveBean> postStartLive(@Field("roomid") int id);


    //开启直播
    @POST("api/live/editorRoom")
    @FormUrlEncoded
    Flowable<UpdateLiveRoomBean> postUpdateLiveRoom(@FieldMap HashMap<String, String> map);



}
