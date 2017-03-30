package com.hyunseok.android.sharemusicplaylist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyunseok.android.sharemusicplaylist.R;

import java.util.List;

/**
 * Playlist Tab 의 RecyclerView Adapter
 *
 * Created by Administrator on 2017-03-30.
 */

public class PlaylistRecyclerViewAdapter extends RecyclerView.Adapter<PlaylistRecyclerViewAdapter.Holder> {

    private Context context;
    private List<String> datas;

    public PlaylistRecyclerViewAdapter(Context context, List<String> datas) {
        this.context = context;
        this.datas = datas;
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

        ImageView imageView_tabitem;
        TextView tv_title_tabitem, tv_artist_tabitem;

        public Holder(View view) {
            super(view);

            imageView_tabitem = (ImageView) view.findViewById(R.id.imageView_tabitem);
            tv_title_tabitem = (TextView) view.findViewById(R.id.tv_title_tabitem);
            tv_artist_tabitem = (TextView) view.findViewById(R.id.tv_artist_tabitem);

//            box.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, PlayerActivity.class);
//                    intent.putExtra(ListFragment.ARG_POSITION, position);
//                    intent.putExtra(ListFragment.ARG_LIST_TYPE, flag);
//                    context.startActivity(intent);
//                }
//            });
        }
    }
}
