package com.hyunseok.android.sharemusicplaylist.player;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hyunseok.android.sharemusicplaylist.R;
import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter;
import com.hyunseok.android.sharemusicplaylist.domain.Track;

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

//    private volatile static Player instance = null;
    private Context context;
    private View view;

    private ViewPager viewPager_player;
    private PlayerAdapter playerAdapter;

    private List<Track> tracks;
    private List<String> dummy;

    public Player(View view, Context context) {
        this.view = view;
        this.context = context;

        tracks = new ArrayList<>();
    }
//    private Player() { }
//
//    public static Player getInstance() {
//        if (instance == null) {
//            synchronized (Player.class) {
//                if (instance == null) {
//                    instance = new Player();
//                }
//            }
//        }
//        return instance;
//    }

    public void execute() {
        initWidget(view, context);

        //play(context);
    }

    private void initWidget(View view, Context context) {
        viewPager_player = (ViewPager) view.findViewById(R.id.viewPager_player);

        dummy = new ArrayList<>();
        dummy.add("Music1");dummy.add("Music2");dummy.add("Music3");

        playerAdapter = new PlayerAdapter(dummy, context);
        viewPager_player.setAdapter(playerAdapter);
    }
    // 음악을 서비스로 실행시킨다.
    private void play(Context context) {
//        Intent intent = new Intent(context, PlayerService.class);
//        intent.setAction(PlayerService.ACTION_PLAY);
//        intent.putExtra("position", position);
//        context.startService(intent);
    }
}
