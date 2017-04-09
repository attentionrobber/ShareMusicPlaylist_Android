package com.hyunseok.android.sharemusicplaylist.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.PlaylistDetailActivity_;
import com.hyunseok.android.sharemusicplaylist.R;

import java.util.List;

/**
 * Playlist Tab 의 RecyclerView Adapter
 *
 * Use by : PlaylistDetailActivity
 *
 * Created by Administrator on 2017-03-30.
 */

public class PlaylistRecyclerViewAdapter_Sample extends RecyclerView.Adapter<PlaylistRecyclerViewAdapter_Sample.Holder> {

    private Context context;
    private List<String> datas;
    private String flag;

    public PlaylistRecyclerViewAdapter_Sample(Context context, List<String> datas, String flag) {
        this.context = context;
        this.datas = datas;
        this.flag = flag;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_search_tab_item, parent, false);
        // TODO layout 새로 만들어서 R.layout.fragment_search_tab_item 바꾸기
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        //final Playlist playlist = datas.get(position);

        holder.position = position; // 현재 위치 받아오기
        holder.tv_title_tabitem.setText(datas.get(position));
        holder.tv_artist_tabitem.setText(datas.get(position));
//        Glide.with(context).load(common.getImageUri())
//                .placeholder(R.mipmap.default_album_image).into(holder.imageView_tabitem);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public int position;

        public int id;
        public String title;

        RelativeLayout itemLayout;
        ImageView imageView_tabitem;
        TextView tv_title_tabitem, tv_artist_tabitem;

        public Holder(View view) {
            super(view);

            itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            imageView_tabitem = (ImageView) view.findViewById(R.id.imageView_tabItem);
            tv_title_tabitem = (TextView) view.findViewById(R.id.tv_title_tabItem);
            tv_artist_tabitem = (TextView) view.findViewById(R.id.tv_artist_tabitem);

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = null;
                    switch (flag) {
                        case "my":
                            Toast.makeText(context, "my", Toast.LENGTH_SHORT).show();
                            // TODO My Playlist 에서만 수정 가능하도록. Following Playlist에서는 수정 불가.
                            intent = new Intent(context, PlaylistDetailActivity_.class);
                            context.startActivity(intent);
                            break;
                        case "follow":
                            Toast.makeText(context, "follow", Toast.LENGTH_SHORT).show();
                            intent = new Intent(context, PlaylistDetailActivity_.class);
                            context.startActivity(intent);
                            break;
                        default: break;
                    }
                }
            });
        }
    }
}
