package com.live.presenter.live;

import com.live.base.BasePresenter;
import com.live.model.api.Callback;
import com.live.model.bean.RoomLiveUrlBean;
import com.live.model.bean.UpdateLiveRoomBean;
import com.live.model.live.UpdateLiveRoomModel;
import com.live.view.live.IRoomLiveUrl;
import com.live.view.live.IUpdateLiveRoom;

import java.util.HashMap;

public class UpdateLiveRoomPresenter extends BasePresenter<IUpdateLiveRoom.View> implements IUpdateLiveRoom.Presenter{

    IUpdateLiveRoom.View view;
    IUpdateLiveRoom.Model model;

    public UpdateLiveRoomPresenter(IUpdateLiveRoom.View view) {
        this.view = view;
        model=new UpdateLiveRoomModel();
    }

    @Override
    public void postUpdateLiveRoom(HashMap<String, String> map) {
        if(view!=null){
            model.postUpdateLiveRoom(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postUpdateLiveRoomResult((UpdateLiveRoomBean) o);
                }
            });
        }
    }
}
