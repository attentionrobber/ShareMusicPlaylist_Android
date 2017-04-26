package com.hyunseok.android.sharemusicplaylist;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hyunseok.android.sharemusicplaylist.adapter.TabPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    TabLayout tabLayout;
    @ViewById
    static
    ViewPager viewPager;
    @ViewById
    Button btn_back;

    public static MainTabFragment search;
    public static MainTabFragment playlist;
    public static MainTabFragment player;
    TabPagerAdapter pagerAdapter; // ViewPager Adapter


    @AfterViews // Define Initialization Code
    protected void init() {
        search = MainTabFragment.newInstance(MainTabFragment.TYPE_SEARCH);
        player = MainTabFragment.newInstance(MainTabFragment.TYPE_PLAYER);
        playlist = MainTabFragment.newInstance(MainTabFragment.TYPE_PLAYLIST);
        setLayout();
    }

    @Click({R.id.btn_back})
    public void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setLayout() {
        // Tab 생성 및 타이틀 입력         //.setIcon(R.mipmap.ic_launcher) // icon 추가가능
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_search)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_player)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_playlist_main)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager
        pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        // 프래그먼트 생성 및 어댑터에 추가 // 첫번째 인자는 열의 개수, 두 번째 인자는 Tab의 flag
        pagerAdapter.add(search); // Search 보기 형식
        pagerAdapter.add(player); // Player 보기 형식
        pagerAdapter.add(playlist); // Playlist 곡 보기 형식

        viewPager.setAdapter(pagerAdapter);
        // 1. 페이저가 변경되었을 때 탭을 바꿔주는 리스너(Pager Listener)
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
        // 2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public static void changeTab(String tab) {
        if ("SEARCH".equals(tab)) {
            viewPager.setCurrentItem(0);
        } else if ("PLAYER".equals(tab)) {
            player.init_playerTab(player.getView());
            player.onResume();
            viewPager.setCurrentItem(1);
        } else if ("PLAYLIST".equals(tab)) {
            playlist.init_playlistTab(playlist.getView());
            playlist.onResume();
            viewPager.setCurrentItem(2);
        }
    }
}