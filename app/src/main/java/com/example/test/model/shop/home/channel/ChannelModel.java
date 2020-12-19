package com.example.test.model.shop.home.channel;

import com.example.test.base.BaseModel;
import com.example.test.model.api.Callback;
import com.example.test.model.bean.shop.home.channel.ChannelBean;
import com.example.test.model.bean.shop.home.channel.ChannelTypeBean;
import com.example.test.net.CommonSubscriber;
import com.example.test.net.HttpManager;
import com.example.test.utils.RxUtils;
import com.example.test.view.shop.home.channel.IChannel;

public class ChannelModel extends BaseModel implements IChannel.BaseModel {

    @Override
    public void getChannel(String id,Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getChannel(id)
                        .compose(RxUtils.rxScheduler())
                        .subscribeWith(new CommonSubscriber<ChannelBean>(callback) {
                            @Override
                            public void onNext(ChannelBean channelBean) {
                                callback.success(channelBean);
                            }
                        })
        );
    }

    @Override
    public void getChannelType(String id,Callback callback) {
        addDisposable(
                HttpManager.getInstance().getService().getChannelType(id)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<ChannelTypeBean>(callback) {
                    @Override
                    public void onNext(ChannelTypeBean channelTypeBean) {
                        callback.success(channelTypeBean);
                    }
                })
        );
    }
}
