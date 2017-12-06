package com.jccy.rxjavaretrofitsamples.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jccy.rxjavaretrofitsamples.BaseFragment;
import com.jccy.rxjavaretrofitsamples.R;
import com.jccy.rxjavaretrofitsamples.adapter.ZbListAdapter;
import com.jccy.rxjavaretrofitsamples.model.ZhuangBiImage;
import com.jccy.rxjavaretrofitsamples.network.NetWork;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class ZbFragment extends BaseFragment {

    private static final String TAG = "ZbFragment";
    private Unbinder unbinder;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ZbListAdapter zbListAdapter;


    @OnClick(R.id.load)
    void onLoadClicked(){
        swipeRefreshLayout.setRefreshing(true);
        loadPictures();
    }

    private void loadPictures() {
        NetWork.getZhuangBiApi()
                .search("110")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<ZhuangBiImage>>() {
                    @Override
                    public void accept(List<ZhuangBiImage> zhuangBiImages) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        zbListAdapter.setImages(zhuangBiImages);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getContext(), "Load failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        zbListAdapter = new ZbListAdapter(getContext());
        recyclerView.setAdapter(zbListAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.zb_fragment_layout;
    }

}