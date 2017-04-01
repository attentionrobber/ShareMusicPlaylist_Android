package com.hyunseok.android.sharemusicplaylist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hyunseok.android.sharemusicplaylist.adapter.PlaylistRecyclerViewAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_playlist_detail)
public class PlaylistDetailActivity extends AppCompatActivity {

    @ViewById
    RecyclerView recyclerView;
    PlaylistRecyclerViewAdapter adapter;
    List<String> playlist;

    @AfterViews
    protected void init() {
        // TODO Recycler View 적용

        playlist = new ArrayList<>();
        playlist.add("Music1");playlist.add("Music2");
        playlist.add("Music3");playlist.add("Music4");
        playlist.add("Music5");playlist.add("Music6");
        playlist.add("Music7");playlist.add("Music8");
        playlist.add("Music9");playlist.add("Music10");
        adapter = new PlaylistRecyclerViewAdapter(this, playlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
