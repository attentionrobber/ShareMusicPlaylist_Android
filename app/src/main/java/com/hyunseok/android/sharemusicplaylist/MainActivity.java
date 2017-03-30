package com.hyunseok.android.sharemusicplaylist;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.hyunseok.android.sharemusicplaylist.adapter.TabPagerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    TabLayout tabLayout;
    @ViewById
    ViewPager viewPager;

    @AfterViews // Define Initialization Code
    protected void init() {

        // Tab 생성 및 타이틀 입력         //.setIcon(R.mipmap.ic_launcher) // icon 추가가능
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_player)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_playlist_main)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_search)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        // 프래그먼트 생성 및 어댑터에 추가 // 첫번째 인자는 열의 개수, 두 번째 인자는 Tab의 flag
        adapter.add(MainTabFragment.newInstance(MainTabFragment.TYPE_PLAYER)); // Player 보기 형식
        adapter.add(MainTabFragment.newInstance(MainTabFragment.TYPE_PLAYLIST)); // Playlist 곡 보기 형식
        adapter.add(MainTabFragment.newInstance(MainTabFragment.TYPE_SEARCH)); // Search 보기 형식

        viewPager.setAdapter(adapter);
        // 1. 페이저가 변경되었을 때 탭을 바꿔주는 리스너(Pager Listener)
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // 2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }
}