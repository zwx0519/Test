package com.example.test.view.shop.home.channel;

import com.example.test.base.IBaseModel;
import com.example.test.base.IBasePersenter;
import com.example.test.base.IBaseView;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.HomeBean;
import com.example.test.model.bean.shop.home.channel.ChannelBean;
import com.example.test.model.bean.shop.home.channel.ChannelTypeBean;
import com.example.test.view.shop.home.IHome;

public interface IChannel { //Channel业务下的View
    interface View extends IBaseView {
        void getChannelReturn(ChannelBean result);
        void getChannelTypeReturn(ChannelTypeBean result);
    }
    //Channel业务下的Presenter
    interface Presenter extends IBasePersenter<IChannel.View> {
        void getChannel(String id);
        void getChannelType(String id);
    }

    //Channel业务下的Model
    interface BaseModel extends IBaseModel {
        void getChannel(String id,Callback callback);
        void getChannelType(String id,Callback callback);
    }
}
