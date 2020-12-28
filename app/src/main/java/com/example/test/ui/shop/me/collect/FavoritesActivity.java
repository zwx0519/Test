package com.example.test.ui.shop.me.collect;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.example.test.R;
import com.example.test.adapter.shop.me.collect.FavoritesAdapter;
import com.example.test.base.BaseActivity;
import com.example.test.base.IBasePersenter;
import com.example.test.model.bean.shop.me.collect.Favorites;
import com.example.test.utils.Realms;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

//TODO 添加数据库
public class FavoritesActivity extends BaseActivity {

    @BindView(R.id.recycler_favorites)
    SwipeMenuRecyclerView mRlv;

    private List<Favorites> list;
    private FavoritesAdapter favoritesAdapter;
    private RealmResults<Favorites> all;

    @Override
    protected int getLayout() {
        return R.layout.activity_favorites;
    }

    @Override
    protected IBasePersenter createPersenter() {
        return null;
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        // 设置菜单创建器
        mRlv.setSwipeMenuCreator(swipeMenuCreator);
        // 设置菜单Item点击监听
        mRlv.setSwipeMenuItemClickListener(menuItemClickListener);

        mRlv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        favoritesAdapter = new FavoritesAdapter(this,list);
        mRlv.setAdapter(favoritesAdapter);

        //查询保存到数据库的值
        all = Realms.getRealm(FavoritesActivity.this).where(Favorites.class).findAll();
        list.clear();
        list.addAll(all);
        favoritesAdapter.notifyDataSetChanged();
    }

    @Override
    protected void initData() {

    }


    //创建侧滑菜单
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            SwipeMenuItem swipeMenuItem = new SwipeMenuItem(FavoritesActivity.this)
                    .setImage(R.mipmap.icon_delete)
                    .setWidth(144)//设置
                    .setHeight(ViewGroup.LayoutParams.MATCH_PARENT);//高（MATCH_PARENT意为Item多高侧滑菜单多高 （推荐使用）;
            swipeRightMenu.addMenuItem(swipeMenuItem);
        }
    };


    //创建侧滑菜单的点击事件
    private SwipeMenuItemClickListener menuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            //在menuBridge中我们可以得到侧滑的这一项item的position (menuBridge.getAdapterPosition())
            int adapterPosition = menuBridge.getAdapterPosition();
            list.remove(adapterPosition);
            //删除数据库
            Realms.getRealm(FavoritesActivity.this).executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    all.get(adapterPosition).deleteFromRealm();
                }
            });
            favoritesAdapter.notifyDataSetChanged();
        }
    };

}