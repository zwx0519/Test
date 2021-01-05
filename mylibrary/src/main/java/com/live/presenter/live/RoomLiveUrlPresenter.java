package com.live.presenter.live;

import com.live.base.BasePresenter;
import com.live.model.api.Callback;
import com.live.model.bean.CreateLiveRoomBean;
import com.live.model.bean.RoomLiveUrlBean;
import com.live.model.live.RoomLiveUrlModel;
import com.live.view.live.IRoomList;
import com.live.view.live.IRoomLiveUrl;

import java.util.HashMap;

//TODO 房间播放地址获取
public class RoomLiveUrlPresenter extends BasePresenter<IRoomLiveUrl.View> implements IRoomLiveUrl.Presenter{

    IRoomLiveUrl.View view;
    IRoomLiveUrl.Model model;

    public RoomLiveUrlPresenter(IRoomLiveUrl.View view) {
        this.view = view;
        model=new RoomLiveUrlModel();
    }

    @Override
    public void postRoomLiveUrl(HashMap<String, String> map) {
        if(view!=null){
            model.postRoomLiveUrl(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postRoomLiveUrlResult((RoomLiveUrlBean) o);
                }
            });
        }
    }
}
