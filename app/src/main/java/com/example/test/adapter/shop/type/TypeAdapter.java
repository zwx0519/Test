package com.example.test.adapter.shop.type;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.type.TypeInfBean;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class TypeAdapter extends BaseAdapter {

    public TypeAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_type_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        TypeInfBean.DataBean.CurrentCategoryBean.SubCategoryListBean bean= (TypeInfBean.DataBean.CurrentCategoryBean.SubCategoryListBean) data;
        ImageView img = (ImageView) vh.getViewById(R.id.iv_type_info_img);
        TextView name = (TextView) vh.getViewById(R.id.tv_type_info_name);

        ImageLoaderUtils.loadImage(bean.getWap_banner_url(),img);
        TxtUtils.setTextView(name,bean.getName());
    }
}
