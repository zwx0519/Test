package com.live.model.live;

import com.live.base.BaseModel;
import com.live.model.api.Callback;
import com.live.model.bean.RoomLiveUrlBean;
import com.live.model.bean.StartLiveBean;
import com.live.net.CommonSubscriber;
import com.live.net.HttpManager;
import com.live.utils.RxUtils;
import com.live.view.live.IRoomLiveUrl;
import com.live.view.live.IStartLive;

//TODO 开启直播
public class StartLiveModel extends BaseModel implements IStartLive.Model {
    @Override
    public void postStartLive(int id, Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().postStartLive(id)
                        .compose(RxUtils.rxScheduler())//线程切换
                        .subscribeWith(new CommonSubscriber<StartLiveBean>(callback) {
                            @Override
                            public void onNext(StartLiveBean startLiveBean) {
                                callback.success(startLiveBean);
                            }

                        })
        );
    }
}
