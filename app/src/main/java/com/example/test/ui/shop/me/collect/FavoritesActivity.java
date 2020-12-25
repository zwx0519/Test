package com.example.test.ui.shop.me.collect;

import android.os.Bundle;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;
import com.example.test.adapter.shop.me.collect.FavoritesAdapter;
import com.example.test.base.BaseActivity;
import com.example.test.base.IBasePersenter;
import com.example.test.model.bean.shop.me.collect.Favorites;
import com.example.test.utils.Realms;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmResults;

public class FavoritesActivity extends BaseActivity {

    @BindView(R.id.recycler_favorites)
    RecyclerView recyclerFavorites;

    private List<Favorites> list;
    private FavoritesAdapter favoritesAdapter;

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
        recyclerFavorites.setLayoutManager(new LinearLayoutManager(this));
        favoritesAdapter = new FavoritesAdapter(this,list);
        recyclerFavorites.setAdapter(favoritesAdapter);
        recyclerFavorites.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        RealmResults<Favorites> all = Realms.getRealm(FavoritesActivity.this).where(Favorites.class).findAll();
        //list.clear();
        list.addAll(all);
        favoritesAdapter.notifyDataSetChanged();

    }

    @Override
    protected void initData() {

    }

}