package com.example.test.ui.shop.type;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.R;
import com.example.test.adapter.shop.type.TypeAdapter;
import com.example.test.app.MyApp;
import com.example.test.base.BaseAdapter;
import com.example.test.base.BaseFragment;
import com.example.test.model.bean.shop.type.TypeBean;
import com.example.test.model.bean.shop.type.TypeInfBean;
import com.example.test.presenter.shop.type.TypePresenter;
import com.example.test.view.shop.type.IType;

import java.util.List;

import butterknife.BindView;

public class TypeInfoFragment extends BaseFragment<IType.Presenter> implements IType.View {
    @BindView(R.id.iv_typeinfo_head_img)
    ImageView iv_Img;
    @BindView(R.id.tv_typeinfo_head_desc)
    TextView tv_Desc;
    @BindView(R.id.tv_typeinfo_title)
    TextView tv_Title;
    @BindView(R.id.mRlv_info)
    RecyclerView mRlv;
    private String id;

    @Override
    protected int getLayout() {
        return R.layout.fragment_type_info;
    }

    @Override
    protected IType.Presenter createPrenter() {
        return new TypePresenter(this);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        presenter.getTypeInfo(id);
    }

    @Override
    public void getTypeReturn(TypeBean result) {

    }

    @Override
    public void getTypeInfoReturn(TypeInfBean result) {
        //TODO 分类数据上面的数据
        Glide.with(getContext()).load(result.getData().getCurrentCategory().getWap_banner_url()).into(iv_Img);
        tv_Desc.setText(result.getData().getCurrentCategory().getFront_desc());
        tv_Title.setText("————"+result.getData().getCurrentCategory().getName()+"分类————");

        //TODO 分类数据
        mRlv.setLayoutManager(new GridLayoutManager(getContext(), 3));
        TypeAdapter typeAdapter = new TypeAdapter(getContext(),result.getData().getCurrentCategory().getSubCategoryList());
        mRlv.setAdapter(typeAdapter);
        typeAdapter.notifyDataSetChanged();

        List<TypeInfBean.DataBean.CurrentCategoryBean.SubCategoryListBean> subCategoryList =
                result.getData().getCurrentCategory().getSubCategoryList();
        typeAdapter.addListClick(new BaseAdapter.IListClick() {
            @Override
            public void itemClick(int pos) {
                Intent intent = new Intent(getActivity(),TypeInfoListActivity.class);
                MyApp.getMap().put("typelist",subCategoryList);

                String name = result.getData().getCurrentCategory().getSubCategoryList().get(pos).getName();
                MyApp.getMap().put("typename", name);

                startActivity(intent);
            }
        });
    }

    public void getId(String id) {
        this.id = id;
    }
}
