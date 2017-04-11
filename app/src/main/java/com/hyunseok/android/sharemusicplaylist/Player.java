package com.hyunseok.android.sharemusicplaylist;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.hyunseok.android.sharemusicplaylist.adapter.PlayerAdapter;

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

    private View view;
    private Context context;

    private ViewPager viewPager_player;
    private PlayerAdapter playerAdapter;

    private List<String> cardDatas;

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

    public void init(View view, Context context) {
        viewPager_player = (ViewPager) view.findViewById(R.id.viewPager_player);

        cardDatas = new ArrayList<>();
        cardDatas.add("card1");
        cardDatas.add("card2");
        cardDatas.add("card3");
        cardDatas.add("card4");
        cardDatas.add("card5");
        cardDatas.add("card6");
        cardDatas.add("card7");
        cardDatas.add("card8");
        cardDatas.add("card9");
        cardDatas.add("card10");

        playerAdapter = new PlayerAdapter(cardDatas, context);
        viewPager_player.setAdapter(playerAdapter);
    }
}
