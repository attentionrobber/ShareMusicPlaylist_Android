package com.hyunseok.android.sharemusicplaylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter_Sample;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;
import com.hyunseok.android.sharemusicplaylist.domain.Track;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Playlist Tab 에서 각 Playlist 를 클릭하면 뜨는 Playlist 상세보기 액티비티
 */

@EActivity(R.layout.activity_playlist_detail)
public class PlaylistDetailActivity extends AppCompatActivity {


    MyApplication myApp;

    @ViewById
    ImageView imageView;
    @ViewById
    TextView tv_playlistTitle;
    @ViewById
    ToggleButton toggle_playlistDetail;
    @ViewById
    RecyclerView recyclerView;
    PlaylistRecyclerViewAdapter_Sample adapter;
    List<Track> playlist = new ArrayList<>();

    // Bundle
    private int position = 0;

    @AfterViews
    protected void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Track track = new Track();
        if(bundle != null) {
            position = bundle.getInt("position");
            String title = bundle.getString("title");
            boolean isShare = bundle.getBoolean("isShare");
            //String tracks = bundle.getString("tracks");
            track.setTitle(bundle.getString("tracks"));
            String imgUri = bundle.getString("imgUri");

            if (imgUri != null) {
                Glide.with(this).load(imgUri)
                        .placeholder(R.mipmap.default_album_image).into(imageView);
            }
            tv_playlistTitle.setText(title);
            toggle_playlistDetail.setChecked(isShare);
            playlist.add(track); // TODO Tracks List<Tracks>로 받도록 바꾸기
        } else {
            // TODO Adapter 바꿀까(?)
            track.setTitle("Playlist1");
            playlist.add(track);
            track.setTitle("Playlist2");
            playlist.add(track);
            track.setTitle("Playlist3");
            playlist.add(track);
            track.setTitle("Playlist4");
            playlist.add(track);
            track.setTitle("Playlist5");
            playlist.add(track);
            track.setTitle("Playlist6");
            playlist.add(track);
            track.setTitle("Playlist7");
            playlist.add(track);
            track.setTitle("Playlist8");
            playlist.add(track);
            track.setTitle("Playlist9");
            playlist.add(track);
            track.setTitle("Playlist10");
            playlist.add(track);
        }

        adapter = new PlaylistRecyclerViewAdapter_Sample(this, playlist, "");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
