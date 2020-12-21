package com.example.test.ui.shop.type;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.test.R;
import com.example.test.base.BaseFragment;
import com.example.test.base.IBasePersenter;
import com.example.test.model.bean.shop.type.TypeBean;
import com.example.test.model.bean.shop.type.TypeInfBean;
import com.example.test.presenter.shop.type.TypePresenter;
import com.example.test.utils.CustomViewPager;
import com.example.test.view.shop.type.IType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;

//TODO 分类展示
public class TypeFragment extends BaseFragment<IType.Presenter> implements IType.View {
    @BindView(R.id.mTab_type)
    VerticalTabLayout mTab;
    @BindView(R.id.mVp_type)
    CustomViewPager mVp;
    private ArrayList<TypeBean.DataBean.CategoryListBean> list;
    private ArrayList<TypeInfoFragment> fragments;

    @Override
    protected int getLayout() {
        return R.layout.fragment_type;
    }

    @Override
    protected IType.Presenter createPrenter() {
        return new TypePresenter(this);
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        fragments = new ArrayList<>();

        //禁止滑动
        mVp.setScanScroll(false);
    }

    @Override
    protected void initData() {
        presenter.getType();
    }

    @Override
    public void getTypeReturn(TypeBean result) {
        List<TypeBean.DataBean.CategoryListBean> categoryList = result.getData().getCategoryList();
        list.addAll(categoryList);

        for (int i = 0; i < list.size(); i++) {
            int id=list.get(i).getId();
            TypeInfoFragment typeInfoFragment = new TypeInfoFragment();
            typeInfoFragment.getId(String.valueOf(id));

            fragments.add(typeInfoFragment);
        }
        VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager());
        mVp.setAdapter(vpAdapter);
        mTab.setupWithViewPager(mVp);

    }

    @Override
    public void getTypeInfoReturn(TypeInfBean result) {

    }


    public class VpAdapter extends FragmentStatePagerAdapter {
        public VpAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return list.get(position).getName();
        }
    }
}
