package com.live.model.live;

import com.live.base.BaseModel;
import com.live.model.api.Callback;
import com.live.model.bean.CreateLiveRoomBean;
import com.live.model.bean.RoomListBean;
import com.live.net.CommonSubscriber;
import com.live.net.HttpManager;
import com.live.utils.RxUtils;
import com.live.view.live.IRoomList;
//TODO 获取房间信息
public class RoomListModel extends BaseModel implements IRoomList.Model {
    @Override
    public void getRoomList(Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getRoomList()
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<RoomListBean>(callback) {
                            @Override
                            public void onNext(RoomListBean roomListBean) {
                                callback.success(roomListBean);
                            }
                        })
        );
    }
}
