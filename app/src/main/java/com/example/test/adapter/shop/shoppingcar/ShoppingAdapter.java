package com.example.test.adapter.shop.shoppingcar;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.shoppingcar.ShoppingCarBean;
import com.example.test.utils.ImageLoaderUtils;
import com.example.test.widget.NumberSelect;
import com.youth.banner.loader.ImageLoader;

import java.util.List;

public class ShoppingAdapter extends BaseAdapter {

    private boolean isEdit; //是否是编辑状态

    private UpdateItem updateItem;

    public void setUpdateItem(UpdateItem item){
        this.updateItem = item;
    }

    public void setEditState(boolean bool){
        isEdit = bool;
    }

    public ShoppingAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_shopping_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        ShoppingCarBean.DataBean.CartListBean bean= (ShoppingCarBean.DataBean.CartListBean) data;

        CheckBox checkBox = (CheckBox) vh.getViewById(R.id.cb_shopping_car_select);
        ImageView img = (ImageView) vh.getViewById(R.id.iv_shopping_car_item_img);
        TextView Name = (TextView) vh.getViewById(R.id.tv_shopping_car_item_name);
        TextView Price = (TextView) vh.getViewById(R.id.tv_shopping_car_item_price);
        TextView Number = (TextView) vh.getViewById(R.id.tv_shopping_car_item_count);
        TextView Title = (TextView) vh.getViewById(R.id.tv_shopping_car_item_title);
        NumberSelect change= (NumberSelect) vh.getViewById(R.id.layout_change);
        LinearLayout linear = (LinearLayout) vh.getViewById(R.id.linear);

        //进行显示隐藏
        Name.setVisibility(isEdit? View.GONE:View.VISIBLE);
        Number.setVisibility(isEdit?View.GONE:View.VISIBLE);
        Title.setVisibility(isEdit?View.VISIBLE:View.GONE);
        change.setVisibility(isEdit?View.VISIBLE:View.GONE);

        // 设置选中状态
        checkBox.setChecked(isEdit?bean.selectEdit:bean.selectOrder);
        ImageLoaderUtils.loadImage(bean.getList_pic_url(),img);
        Price.setText("￥"+bean.getRetail_price());
        Name.setText(bean.getGoods_name());
        Number.setText(String.valueOf(bean.getNumber()));

        change.addPage(R.layout.layout_number_change);
        change.addChangeNumber(new NumberSelect.ChangeNumber() {
            @Override
            public void change(int number) {
                //修改本地数据得值
                bean.setNumber(number);
                if(updateItem != null){
                    updateItem.updateItemDate(bean);
                }
            }
        });

        checkBox.setTag(bean.getId());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(iItemViewClick != null){
                    int id = (int) buttonView.getTag();
                    iItemViewClick.itemViewClick(id,isChecked);//接口回调
                }
            }
        });

//        if (isEdit) {
//            Number.setVisibility(View.GONE);
//            Title.setVisibility(View.VISIBLE);
//            linear.setVisibility(View.VISIBLE);
//
//        } else {
//            Number.setVisibility(View.VISIBLE);
//            Title.setVisibility(View.GONE);
//            linear.setVisibility(View.GONE);
//        }
//
//        if (checked) {
//            checkBox.isSelected();
//        }
    }

    //修改条目
    public interface UpdateItem{
        void updateItemDate(ShoppingCarBean.DataBean.CartListBean data);
    }
}
