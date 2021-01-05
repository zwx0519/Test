package com.live.model.live;


import com.live.base.BaseModel;
import com.live.model.api.Callback;
import com.live.model.bean.CreateLiveRoomBean;
import com.live.net.CommonSubscriber;
import com.live.net.HttpManager;
import com.live.utils.RxUtils;
import com.live.view.live.ICreateRoom;

import java.util.HashMap;

//TODO 创建直播房间
public class CreateLiveRoomModel extends BaseModel implements ICreateRoom.Model{

    @Override
    public void postCreateRoom(HashMap<String, String> map, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postCreateRoom(map)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<CreateLiveRoomBean>(callback) {
                            @Override
                            public void onNext(CreateLiveRoomBean createLiveRoomBean) {
                                callback.success(createLiveRoomBean);
                            }
                        })
        );
    }
}
