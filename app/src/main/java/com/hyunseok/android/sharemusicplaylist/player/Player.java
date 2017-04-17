package com.hyunseok.android.sharemusicplaylist.player;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.test.RenamingDelegatingContext;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter_sample;
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
    private Context context;
    private View view;

    private ViewPager viewPager_player;
    private PlayerAdapter playerAdapter;
    private PlayerAdapter_sample playerAdapter_sample;

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
        initWidget(view, context);

        Log.i("PlayingTest", "execute()");
    }

    private void initWidget(View view, Context context) {
        viewPager_player = (ViewPager) view.findViewById(R.id.viewPager_player);

        //Toast.makeText(context, ""+Track_Extracted.title, Toast.LENGTH_SHORT).show();

        playerAdapter = new PlayerAdapter(tracks, context);
        viewPager_player.setAdapter(playerAdapter);
    }

    // 음악을 서비스로 실행시킨다.
    public static void play(Context context) {
        Intent intent = new Intent(context, PlayerService.class);
        intent.setAction(PlayerService.ACTION_PLAY);
        intent.putExtra("position", Track_Extracted.position);
        context.startService(intent);
    }
}
