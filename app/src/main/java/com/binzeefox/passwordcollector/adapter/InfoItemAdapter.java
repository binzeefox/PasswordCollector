package com.binzeefox.passwordcollector.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.binzeefox.passwordcollector.InfoListActivity;
import com.binzeefox.passwordcollector.R;

import java.util.List;

/**
 * Created by tong.xiwen on 2017/3/22.
 */
public class InfoItemAdapter extends RecyclerView.Adapter<InfoItemAdapter.ViewHolder> {

    private InfoListActivity activity;

    private List<InfoItem> mItemList;
    static class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView title;
        TextView userName;


        public ViewHolder(View view) {
            super(view);
            itemView = view;
            title = (TextView) view.findViewById(R.id.item_title);
            userName = (TextView) view.findViewById(R.id.item_userName);
        }
    }

    public InfoItemAdapter(List<InfoItem> itemList) {
        mItemList = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                InfoItem infoItem = mItemList.get(position);
                String select = infoItem.getTitle();
                // TODO 在下面写入InfoListActivity的方法算法

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InfoItem infoItem = mItemList.get(position);
        holder.title.setText(infoItem.getTitle());
        holder.userName.setText(infoItem.getMessage());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
