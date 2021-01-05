package com.live.presenter.live;


import com.live.base.BasePresenter;
import com.live.model.api.Callback;
import com.live.model.bean.CreateLiveRoomBean;
import com.live.model.live.CreateLiveRoomModel;
import com.live.view.live.ICreateRoom;

import java.util.HashMap;

//TODO 创建直播房间
public class CreateLiveRoomPresenter extends BasePresenter<ICreateRoom.View> implements ICreateRoom.Presenter{
    ICreateRoom.View view;
    ICreateRoom.Model model;

    public CreateLiveRoomPresenter(ICreateRoom.View view) {
        this.view = view;
        model=new CreateLiveRoomModel();
    }

    @Override
    public void postCreateRoom(HashMap<String, String> map) {
        if(view!=null){
            model.postCreateRoom(map, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.postCreateRoomResult((CreateLiveRoomBean) o);
                }
            });
        }
    }
}
