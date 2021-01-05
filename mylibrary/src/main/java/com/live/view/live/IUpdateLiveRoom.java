package com.live.view.live;

import com.live.base.IBaseModel;
import com.live.base.IBasePersenter;
import com.live.base.IBaseView;
import com.live.model.api.Callback;
import com.live.model.bean.StartLiveBean;
import com.live.model.bean.UpdateLiveRoomBean;

import java.util.HashMap;

//TODO 修改直播间
public interface IUpdateLiveRoom {

    interface View extends IBaseView {
        void postUpdateLiveRoomResult(UpdateLiveRoomBean updateLiveRoomBean);
    }

    interface Presenter extends IBasePersenter<IUpdateLiveRoom.View> {
        void postUpdateLiveRoom(HashMap<String, String> map);
    }


    interface Model extends IBaseModel {
        void postUpdateLiveRoom(HashMap<String, String> map, Callback callback);
    }
}
