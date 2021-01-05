package com.live.view.live;

import com.live.base.IBaseModel;
import com.live.base.IBasePersenter;
import com.live.base.IBaseView;
import com.live.model.api.Callback;
import com.live.model.bean.RoomListBean;
import com.live.model.bean.RoomLiveUrlBean;

import java.util.HashMap;

//TODO 房间播放地址获取
public interface IRoomLiveUrl {
    interface View extends IBaseView {
        void postRoomLiveUrlResult(RoomLiveUrlBean roomLiveUrlBean);
    }

    interface Presenter extends IBasePersenter<IRoomLiveUrl.View> {
        void postRoomLiveUrl(HashMap<String, String> map);
    }


    interface Model extends IBaseModel {
        void postRoomLiveUrl(HashMap<String, String> map,Callback callback);
    }
}
