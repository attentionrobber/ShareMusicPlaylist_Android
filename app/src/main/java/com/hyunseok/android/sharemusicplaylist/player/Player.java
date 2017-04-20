package com.hyunseok.android.sharemusicplaylist.player;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter_sample;
import com.hyunseok.android.sharemusicplaylist.adapter.Player_PlaylistAdapter;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.domain.Track_Extracted;

import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity 에서
 * 음악 재생 Class
 *
 * Used in : MainTabFragment
 * Created by KHS on 2017-04-11.
 */

public class Player {

    private volatile static Player instance = null;

    public static Intent intent = null;

    private Context context;
    private View view;

    // Widget
    private ViewPager viewPager_player;
    private PlayerAdapter playerAdapter;
    private PlayerAdapter_sample playerAdapter_sample;

    private RelativeLayout relative_playlist;
    private ListView listView;
    private Player_PlaylistAdapter playlistAdapter;

    private ImageButton imgbtn_play, imgbtn_prev, imgbtn_next, imgbtn_playlist;

    // Data
    private List<Track> tracks;

    //    public Player(View view, Context context) {
//        this.view = view;
//        this.context = context;
//
//        tracks = new ArrayList<>();
//    }
    private Player() { }

    public static Player getInstance() {
        if (instance == null) {
            synchronized (Player.class) {
                if (instance == null) {
                    instance = new Player();
                }
            }
        }
        return instance;
    }

    public void execute(View view, Context context) {

        tracks = new ArrayList<>();
        tracks = Track_Extracted.tracks;
        initWidget(view, context);

        Log.i("PlayingTest", "execute()");
    }

    private void initWidget(View view, Context context) {

        relative_playlist = (RelativeLayout) view.findViewById(R.id.relative_playlist);
        imgbtn_prev = (ImageButton) view.findViewById(R.id.imgbtn_prev);
        imgbtn_play = (ImageButton) view.findViewById(R.id.imgbtn_play);
        imgbtn_next = (ImageButton) view.findViewById(R.id.imgbtn_next);
        imgbtn_playlist = (ImageButton) view.findViewById(R.id.imgbtn_playlist);
        imgbtn_prev.setOnClickListener(clickListener);
        imgbtn_play.setOnClickListener(clickListener);
        imgbtn_next.setOnClickListener(clickListener);
        imgbtn_playlist.setOnClickListener(clickListener);

        // Player 카드 item(곡 재생 정보)
        viewPager_player = (ViewPager) view.findViewById(R.id.viewPager_player);
        playerAdapter = new PlayerAdapter(tracks, context);
        viewPager_player.setAdapter(playerAdapter);


        // 플레이어의 현재 재생목록(Playlist) 적용 부분
        playlistAdapter = new Player_PlaylistAdapter(tracks, context);
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(playlistAdapter);
    }

    // 음악을 서비스로 실행시킨다.
    public static void play(Context context) {
        if (intent == null) {
            Intent intent = new Intent(context, PlayerService.class);
            intent.setAction(PlayerService.ACTION_PLAY);
            //intent.putExtra("position", Track_Extracted.position);
            context.startService(intent);
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgbtn_prev:
                    break;
                case R.id.imgbtn_play:
                    break;
                case R.id.imgbtn_next:
                    break;
                case R.id.imgbtn_playlist:
                    Log.i("PlayerTest", "clickEvent");
                    if (relative_playlist.getVisibility() == View.GONE) {
                        relative_playlist.setVisibility(View.VISIBLE);
                    } else if (relative_playlist.getVisibility() == View.VISIBLE) {
                        relative_playlist.setVisibility(View.GONE);
                    }
                    break;
                default: break;
            }
        }
    };
}
