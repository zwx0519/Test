package com.example.test.presenter.shop.home.channel;

import com.example.test.base.BasePresenter;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.channel.ChannelBean;
import com.example.test.model.bean.shop.home.channel.ChannelTypeBean;
import com.example.test.model.shop.home.channel.ChannelModel;
import com.example.test.view.shop.home.IHome;
import com.example.test.view.shop.home.channel.IChannel;

public class ChannelPresenter extends BasePresenter<IChannel.View> implements IChannel.Presenter {
    IChannel.View view;
    IChannel.BaseModel model;

    public ChannelPresenter(IChannel.View view) {
        this.view = view;
        this.model=new ChannelModel();
    }

    @Override
    public void getChannel(String id) {
        if(view!=null){
            this.model.getChannel(id,new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getChannelReturn((ChannelBean) o);
                }
            });
        }
    }

    @Override
    public void getChannelType(String id) {
        if(view!=null){
            this.model.getChannelType(id, new Callback() {
                @Override
                public void fail(String msg) {
                    view.tips(msg);
                }

                @Override
                public void success(Object o) {
                    view.getChannelTypeReturn((ChannelTypeBean) o);
                }
            });
        }
    }
}
