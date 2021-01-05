package com.live.view.live;

import com.live.base.IBaseModel;
import com.live.base.IBasePersenter;
import com.live.base.IBaseView;
import com.live.model.api.Callback;
import com.live.model.bean.CreateLiveRoomBean;

import java.util.HashMap;

//TODO 创建直播房间
public interface ICreateRoom {

    interface View extends IBaseView {
        void postCreateRoomResult(CreateLiveRoomBean createLiveRoomBean);
    }

    interface Presenter extends IBasePersenter<ICreateRoom.View> {
        void postCreateRoom(HashMap<String, String> map);
    }


    interface Model extends IBaseModel {
        void postCreateRoom(HashMap<String, String> map, Callback callback);
    }
}
