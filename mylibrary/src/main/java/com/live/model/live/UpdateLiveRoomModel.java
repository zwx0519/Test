package com.live.model.live;

import com.live.base.BaseModel;
import com.live.model.api.Callback;
import com.live.model.bean.StartLiveBean;
import com.live.model.bean.UpdateLiveRoomBean;
import com.live.net.CommonSubscriber;
import com.live.net.HttpManager;
import com.live.utils.RxUtils;
import com.live.view.live.IStartLive;
import com.live.view.live.IUpdateLiveRoom;

import java.util.HashMap;

public class UpdateLiveRoomModel extends BaseModel implements IUpdateLiveRoom.Model{
    @Override
    public void postUpdateLiveRoom(HashMap<String, String> map, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postUpdateLiveRoom(map)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<UpdateLiveRoomBean>(callback) {
                            @Override
                            public void onNext(UpdateLiveRoomBean updateLiveRoomBean) {
                                callback.success(updateLiveRoomBean);
                            }

                        })
        );
    }
}
