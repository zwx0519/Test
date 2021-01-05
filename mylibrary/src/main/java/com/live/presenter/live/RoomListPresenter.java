package com.live.presenter.live;

import com.live.base.BasePresenter;
import com.live.model.api.Callback;
import com.live.model.bean.CreateLiveRoomBean;
import com.live.model.bean.RoomListBean;
import com.live.model.live.RoomListModel;
import com.live.view.live.IRoomList;

//TODO 获取房间信息
public class RoomListPresenter extends BasePresenter<IRoomList.View> implements IRoomList.Presenter {
    IRoomList.View view;
    IRoomList.Model model;

    public RoomListPresenter(IRoomList.View view) {
        this.view = view;
        model = new RoomListModel();
    }

    @Override
    public void getRoomList() {
        if (view != null) {
            model.getRoomList(new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getRoomListResult((RoomListBean) o);
                }
            });
        }
    }
}
