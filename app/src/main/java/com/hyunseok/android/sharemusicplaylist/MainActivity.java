package com.hyunseok.android.sharemusicplaylist;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.hyunseok.android.sharemusicplaylist.adapter.TabPagerAdapter;
import com.hyunseok.android.sharemusicplaylist.util.PermissionControl;


import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    static ViewPager viewPager;
    Button btn_back;

    public static MainTabFragment search;
    public static MainTabFragment playlist;
    public static MainTabFragment player;
    TabPagerAdapter pagerAdapter; // ViewPager Adapter
    private static int REQ = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setWidget();
        init();
    }

    private void setWidget() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        btn_back = findViewById(R.id.btn_back);
    }

    protected void init() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionControl.checkPermission(this, REQ);
        }
        search = MainTabFragment.newInstance(MainTabFragment.TYPE_SEARCH);
        player = MainTabFragment.newInstance(MainTabFragment.TYPE_PLAYER);
        playlist = MainTabFragment.newInstance(MainTabFragment.TYPE_PLAYLIST);
        setLayout();
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

    public void goLogin() {
        // TODO 버튼 없애기
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionControl.onCheckResult(grantResults);
    }
}