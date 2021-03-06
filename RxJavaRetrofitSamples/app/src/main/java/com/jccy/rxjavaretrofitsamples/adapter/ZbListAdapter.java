package com.jccy.rxjavaretrofitsamples.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jccy.rxjavaretrofitsamples.R;
import com.jccy.rxjavaretrofitsamples.model.ZhuangBiImage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by heyangyang on 2017/12/6.
 */

public class ZbListAdapter extends RecyclerView.Adapter<ZbListAdapter.ZbViewHolder> {

    private Context mContext;
    List<ZhuangBiImage> mData = new ArrayList<>();

    public ZbListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ZbViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        这种创建View的方式不太好，在layout中设置的margin都不管用，因为它没有参照对象
//        View inflate = View.inflate(mContext, R.layout.zb_list_item_layout, null);
        //这种生成View的方式因为有parent做参照对象 布局会好看点
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.zb_list_item_layout, parent, false);
        return new ZbViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ZbViewHolder holder, int position) {
        ZhuangBiImage zhuangBiImage = mData.get(position);
        holder.des.setText(zhuangBiImage.description);
        Glide.with(mContext).load(zhuangBiImage.image_url).into(holder.image);
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setImages(List<ZhuangBiImage> zhuangBiImages) {
        mData.clear();
        mData.addAll(zhuangBiImages);
        notifyDataSetChanged();
    }

    static class ZbViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.des)
        TextView des;
        public ZbViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
