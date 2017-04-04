package com.hyunseok.android.sharemusicplaylist;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    RecyclerView recyclerView;
    PlaylistRecyclerViewAdapter_Sample adapter;
    List<String> playlist;

    @AfterViews
    protected void init() {

        // TODO Adapter 바꿀까(?)
        playlist = new ArrayList<>();
        playlist.add("Playlist1");playlist.add("Playlist2");
        playlist.add("Playlist3");playlist.add("Playlist4");
        playlist.add("Playlist5");playlist.add("Playlist6");
        playlist.add("Playlist7");playlist.add("Playlist8");
        playlist.add("Playlist9");playlist.add("Playlist10");
        adapter = new PlaylistRecyclerViewAdapter_Sample(this, playlist, "");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
