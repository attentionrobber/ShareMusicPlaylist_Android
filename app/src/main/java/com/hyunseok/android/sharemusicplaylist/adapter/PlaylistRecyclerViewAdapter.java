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

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.PlaylistDetailActivity_;
import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;

import java.util.List;

/**
 * Playlist Tab 의 RecyclerView Adapter
 *
 * Use by : MainTabFragment, PlaylistDetailActivity
 *
 * Created by Administrator on 2017-03-30.
 */

public class PlaylistRecyclerViewAdapter extends RecyclerView.Adapter<PlaylistRecyclerViewAdapter.Holder> {

    private Context context;
    private List<Playlist> datas;
    private String flag;
    Intent intent; // PlaylistDetail Activity

    public PlaylistRecyclerViewAdapter(Context context, List<Playlist> datas, String flag) {
        this.context = context;
        this.datas = datas;
        this.flag = flag;
        intent = new Intent(context, PlaylistDetailActivity_.class);
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

        // TODO Field 에 맞게 holder 추가하기.

        final Playlist playlist = datas.get(position);
        List<String> tracks = playlist.getTracks();

        holder.position = position; // 현재 위치 받아오기
        holder.tv_title_tabitem.setText(playlist.getTitle());
        holder.tv_artist_tabitem.setText(tracks.toString());
        holder.imgUri = playlist.getImgUri();
        Glide.with(context).load(holder.imgUri)
                .placeholder(R.mipmap.default_album_image).into(holder.imageView_tabitem);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        public int position;

        public int id;
        public String title;
        public String imgUri;

        RelativeLayout itemLayout;
        ImageView imageView_tabitem;
        TextView tv_title_tabitem, tv_artist_tabitem;

        public Holder(View view) {
            super(view);

            itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            imageView_tabitem = (ImageView) view.findViewById(R.id.imageView_tabitem);
            tv_title_tabitem = (TextView) view.findViewById(R.id.tv_title_tabitem);
            tv_artist_tabitem = (TextView) view.findViewById(R.id.tv_artist_tabitem);

            itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (flag) {
                        case "my":
                            // TODO My Playlist 에서만 수정 가능하도록. Following Playlist에서는 수정 불가.
                            // TODO 클릭시 해당 PlaylistDetailActivity 띄우기
                            goMyPlaylistDetail();
                            break;
                        case "follow":
                            goFollowPlaylistDetail();
                            break;
                        default: break;
                    }
                }
            });
        }

        private void goMyPlaylistDetail() {
            intent.putExtra("position", position);
            intent.putExtra("title", tv_title_tabitem.getText().toString()); // Playlist Title
            intent.putExtra("tracks", tv_artist_tabitem.getText().toString()); // Playlist Tracks // TODO tv_artist_tabitem을 Track List로 바꾸기
            intent.putExtra("imgUri", imgUri); // Playlist 대표 이미지 Uri
            context.startActivity(intent);
        }

        private void goFollowPlaylistDetail() {
            intent = new Intent(context, PlaylistDetailActivity_.class);
            context.startActivity(intent);
        }
    }
}
