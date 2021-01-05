package com.live.model.live;

import com.live.base.BaseModel;
import com.live.model.api.Callback;
import com.live.model.bean.RoomListBean;
import com.live.model.bean.RoomLiveUrlBean;
import com.live.net.CommonSubscriber;
import com.live.net.HttpManager;
import com.live.utils.RxUtils;
import com.live.view.live.IRoomList;
import com.live.view.live.IRoomLiveUrl;

import java.util.HashMap;

//TODO 房间播放地址获取
public class RoomLiveUrlModel extends BaseModel implements IRoomLiveUrl.Model{

    @Override
    public void postRoomLiveUrl(HashMap<String, String> map, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postRoomLiveUrl(map)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<RoomLiveUrlBean>(callback) {
                            @Override
                            public void onNext(RoomLiveUrlBean roomLiveUrlBean) {
                                callback.success(roomLiveUrlBean);
                            }
                        })
        );
    }
}
