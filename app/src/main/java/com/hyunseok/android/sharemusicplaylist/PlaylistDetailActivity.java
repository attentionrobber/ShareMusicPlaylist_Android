package com.hyunseok.android.sharemusicplaylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter_Sample;
import com.hyunseok.android.sharemusicplaylist.domain.Playlist;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_playlist_detail)
public class PlaylistDetailActivity extends AppCompatActivity {

    @ViewById
    ImageView imageView;
    @ViewById
    TextView tv_playlistTitle;
    @ViewById
    RecyclerView recyclerView;
    PlaylistRecyclerViewAdapter_Sample adapter;
    List<String> playlist = new ArrayList<>();

    // Bundle
    private int position = 0;

    @AfterViews
    protected void init() {

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null) {
            position = bundle.getInt("position");
            String title = bundle.getString("title");
            String tracks = bundle.getString("tracks");
            String imgUri = bundle.getString("imgUri");
            if (imgUri != null) {
                Glide.with(this).load(imgUri)
                        .placeholder(R.mipmap.default_album_image).into(imageView);
            }
            tv_playlistTitle.setText(title);
            playlist.add(tracks);
        } else {
            // TODO Adapter 바꿀까(?)
            playlist.add("Playlist1");
            playlist.add("Playlist2");
            playlist.add("Playlist3");
            playlist.add("Playlist4");
            playlist.add("Playlist5");
            playlist.add("Playlist6");
            playlist.add("Playlist7");
            playlist.add("Playlist8");
            playlist.add("Playlist9");
            playlist.add("Playlist10");
        }

        adapter = new PlaylistRecyclerViewAdapter_Sample(this, playlist, "");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
