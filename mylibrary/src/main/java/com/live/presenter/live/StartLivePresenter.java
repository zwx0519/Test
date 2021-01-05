package com.live.presenter.live;

import com.live.base.BasePresenter;
import com.live.model.api.Callback;
import com.live.model.bean.RoomLiveUrlBean;
import com.live.model.bean.StartLiveBean;
import com.live.model.live.StartLiveModel;
import com.live.view.live.IRoomLiveUrl;
import com.live.view.live.IStartLive;

//TODO 开启直播
public class StartLivePresenter  extends BasePresenter<IStartLive.View> implements IStartLive.Presenter{

    IStartLive.View view;
    IStartLive.Model model;

    public StartLivePresenter(IStartLive.View view) {
        this.view = view;
        model=new StartLiveModel();
    }

    @Override
    public void postStartLive(int id) {
        if(view!=null){
            model.postStartLive(id, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postStartLiveResult((StartLiveBean) o);
                }
            });
        }
    }
}
