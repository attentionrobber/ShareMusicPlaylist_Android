package com.hyunseok.android.sharemusicplaylist.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter_sample;
import com.hyunseok.android.sharemusicplaylist.adapter.Player_PlaylistAdapter;
import com.hyunseok.android.sharemusicplaylist.domain.Track;
import com.hyunseok.android.sharemusicplaylist.domain.Track_Extracted;

import java.util.ArrayList;
import java.util.List;

import static com.hyunseok.android.sharemusicplaylist.R.id.seekBar;
import static com.hyunseok.android.sharemusicplaylist.player.PlayerService.mMediaPlayer;
import static com.hyunseok.android.sharemusicplaylist.player.PlayerService.position;

/**
 * MainActivity 에서
 * 음악 재생 Class
 *
 * Used in : MainTabFragment
 * Created by KHS on 2017-04-11.
 */

public class Player implements ControlInterface{

    private volatile static Player instance = null;

    public static Intent intent = null;

    private static Activity activity;
    private Context context;
    private View view;

    // Widget
    private ViewPager viewPager_player;
    private PlayerAdapter playerAdapter;
    private PlayerAdapter_sample playerAdapter_sample;

    private RelativeLayout relative_playlist;
    private RecyclerView rv_playlist;
    private Player_PlaylistAdapter playlistAdapter;

    private ImageButton imgbtn_play, imgbtn_prev, imgbtn_next, imgbtn_playlist;
    public static SeekBar seekBar;
    public static TextView tv_current, tv_duration;

    // Data
    private List<Track> tracks;

    private Controller controller;

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

    public void execute(View view, Activity activity, Context context) {
        this.activity = activity;
        this.context = context;

        controller = Controller.getInstance();
        controller.addObserver(this); // 옵저버패턴 적용

        tracks = new ArrayList<>();
        tracks = Track_Extracted.tracks;

        initWidget(view, context);
    }

    private void initWidget(View view, Context context) {

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);
        tv_current = (TextView) view.findViewById(R.id.tv_current);
        tv_duration = (TextView) view.findViewById(R.id.tv_duration);
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
        viewPager_player.setCurrentItem(Track_Extracted.tracks.size()-1); // TODO 바꿀필요있음. 문제점 : 임의의 트랙을 선택 재생했을 때 size-1로 화면 전환하는게 맞지 않음.

        // 플레이어의 현재 재생목록(Playlist) 적용 부분
        playlistAdapter = new Player_PlaylistAdapter(tracks, context);
        rv_playlist = (RecyclerView) view.findViewById(R.id.rv_playlist);
        rv_playlist.setLayoutManager(new LinearLayoutManager(context));
        rv_playlist.setAdapter(playlistAdapter);
    }

    // 음악을 서비스로 실행시킨다.
    public static void play(Context context, String action) {
        //if (intent == null) {
            Intent intent = new Intent(context, PlayerService.class);
            //intent.setAction(PlayerService.ACTION_PLAY);
            intent.setAction(action);
            //intent.putExtra("position", Track_Extracted.position);
            context.startService(intent);

        controllerInit();

        Thread t = new TimerThread();
        t.start();

        //}
    }

    private static void controllerInit() {
        int duration = Track_Extracted.tracks.get(position).getDuration();
        int min = duration / 60;
        int sec = duration % 60;
        String str_m = String.format("%02d", min);
        String str_s = String.format("%02d", sec);

        tv_duration.setText(str_m + ":" +str_s);
        seekBar.setMax(duration);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.imgbtn_prev:
                    prevPlayer();
                    break;
                case R.id.imgbtn_play:
                    play(context, PlayerService.ACTION_PLAY);
                    break;
                case R.id.imgbtn_next:
                    nextPlayer();
                    break;
                case R.id.imgbtn_playlist:
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

    @Override
    public void startPlayer() {
        Toast.makeText(context, "AA", Toast.LENGTH_SHORT).show();
        imgbtn_play.setImageResource(android.R.drawable.ic_media_pause);
    }

    @Override
    public void pausePlayer() {
        Toast.makeText(context, "BB", Toast.LENGTH_SHORT).show();
        imgbtn_play.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void prevPlayer() {
    
    }

    @Override
    public void nextPlayer() {

    }

    /**
     * SeekBar와 남은시간(TextView)을 실행하는 쓰레드
     */
    static class TimerThread extends Thread {
        @Override
        public void run() {
            while(true) {
                if(mMediaPlayer != null) {
                    activity.runOnUiThread(() -> {
                        int sec = mMediaPlayer.getCurrentPosition() / 1000;
                        int min = sec / 60;
                        String str_m = String.format("%02d", min);
                        String str_s = String.format("%02d", sec);

                        try {
                            seekBar.setProgress(mMediaPlayer.getCurrentPosition() / 1000); // 프로그레스바 진행 표시
                            tv_current.setText(str_m + ":" + str_s);
                        } catch (Exception e) { e.printStackTrace(); }
                    });
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
