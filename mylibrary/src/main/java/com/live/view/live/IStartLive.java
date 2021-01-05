package com.live.view.live;

import com.live.base.IBaseModel;
import com.live.base.IBasePersenter;
import com.live.base.IBaseView;
import com.live.model.api.Callback;
import com.live.model.bean.StartLiveBean;

//TODO 开启直播
public interface IStartLive {

    interface View extends IBaseView {
        void postStartLiveResult(StartLiveBean startLiveBean);
    }

    interface Presenter extends IBasePersenter<IStartLive.View> {
        void postStartLive(int id);
    }


    interface Model extends IBaseModel {
        void postStartLive(int id,Callback callback);
    }
}
