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
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.PlaylistDetailActivity;
import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;
import com.hyunseok.android.sharemusicplaylist.domain.Track;

import java.util.List;

/**
 * Playlist Tab 의 RecyclerView Adapter
 *
 * Use by : MainTabFragment
 *
 * Created by Administrator on 2017-03-30.
 */

public class PlaylistRecyclerViewAdapter extends RecyclerView.Adapter<PlaylistRecyclerViewAdapter.Holder> {

    private Context context;
    private List<Playlist> datas;
    private String flag;
    private Intent intent; // PlaylistDetail Activity

    public PlaylistRecyclerViewAdapter(Context context, List<Playlist> datas, String flag) {
        this.context = context;
        this.datas = datas;
        this.flag = flag;
        intent = new Intent(context, PlaylistDetailActivity.class);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.main_playlist_tab_item, parent, false);
        // TODO layout 새로 만들어서 R.layout.fragment_search_tab_item 바꾸기
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        // TODO Field 에 맞게 holder 추가하기.

        final Playlist playlist = datas.get(position);
        List<Track> tracks = playlist.getTracks();

        holder.position = position; // 현재 위치 받아오기
        holder.tv_title_tabItem.setText(playlist.getTitle());
        holder.tv_nickname_tabItem.setText("nickname");
        holder.tv_tracks_tabItem.setText(tracks.toString());
        holder.toggle_mainPlaylist.setChecked(playlist.getIsShare());
        holder.imgUri = playlist.getImgUri();
        Glide.with(context).load(holder.imgUri)
                .placeholder(R.mipmap.default_album_image).into(holder.imageView_tabItem);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        private int position;

        String imgUri;

        RelativeLayout itemLayout;
        ImageView imageView_tabItem;
        TextView tv_title_tabItem, tv_nickname_tabItem, tv_tracks_tabItem;
        ToggleButton toggle_mainPlaylist;

        Holder(View view) {
            super(view);

            itemLayout = (RelativeLayout) view.findViewById(R.id.itemLayout);
            imageView_tabItem = (ImageView) view.findViewById(R.id.imageView_tabItem);
            tv_title_tabItem = (TextView) view.findViewById(R.id.tv_title_tabItem);
            tv_nickname_tabItem = (TextView) view.findViewById(R.id.tv_nickname_tabItem);
            tv_tracks_tabItem = (TextView) view.findViewById(R.id.tv_tracks_tabItem);
            toggle_mainPlaylist = (ToggleButton) view.findViewById(R.id.toggle_mainPlaylist);

            itemLayout.setOnClickListener(v -> {
                switch (flag) {
                    case "my":
                        // TODO My Playlist 에서만 수정 가능하도록. Following Playlist에서는 수정 불가.
                        goMyPlaylistDetail();
                        break;
                    case "follow":
                        goFollowPlaylistDetail();
                        break;
                    default: break;
                }
            });
        }

        private void goMyPlaylistDetail() {
            intent.putExtra("position", position);
            intent.putExtra("title", tv_title_tabItem.getText().toString()); // Playlist Title
            intent.putExtra("tracks", tv_nickname_tabItem.getText().toString()); // Playlist Tracks // TODO tv_artist_tabitem을 Track List로 바꾸기
            intent.putExtra("isShare", toggle_mainPlaylist.isChecked());
            intent.putExtra("imgUri", imgUri); // Playlist 대표 이미지 Uri
            context.startActivity(intent);
        }

        private void goFollowPlaylistDetail() {
            intent = new Intent(context, PlaylistDetailActivity.class);
            context.startActivity(intent);
        }
    }
}
