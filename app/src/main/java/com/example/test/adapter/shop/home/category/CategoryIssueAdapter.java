package com.example.test.adapter.shop.home.category;

import android.content.Context;
import android.widget.TextView;

import com.example.test.R;
import com.example.test.base.BaseAdapter;
import com.example.test.model.bean.shop.home.category.CategoryBean;
import com.example.test.utils.TxtUtils;

import java.util.List;

public class CategoryIssueAdapter extends BaseAdapter {

    public CategoryIssueAdapter(Context context, List Data) {
        super(context, Data);
    }

    @Override
    protected int getLayout(int type) {
        return R.layout.layout_category_issue_item;
    }

    @Override
    protected void bindData(Object data, VH vh) {
        CategoryBean.DataBeanX.IssueBean bean = (CategoryBean.DataBeanX.IssueBean) data;
        TextView key= (TextView) vh.getViewById(R.id.tv_category_issue_key);
        TextView value= (TextView) vh.getViewById(R.id.tv_category_issue_value);

        TxtUtils.setTextView(key,bean.getQuestion());
        TxtUtils.setTextView(value,bean.getAnswer());
    }
}
