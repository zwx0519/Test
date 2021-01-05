package com.live.view.live;

import com.live.base.IBaseModel;
import com.live.base.IBasePersenter;
import com.live.base.IBaseView;
import com.live.model.api.Callback;
import com.live.model.bean.CreateLiveRoomBean;
import com.live.model.bean.RoomListBean;

import java.util.HashMap;

//TODO 获取房间信息
public interface IRoomList {
    interface View extends IBaseView {
        void getRoomListResult(RoomListBean roomListBean);
    }

    interface Presenter extends IBasePersenter<IRoomList.View> {
        void getRoomList();
    }

    interface Model extends IBaseModel {
        void getRoomList(Callback callback);
    }
}
