package com.hyunseok.android.sharemusicplaylist;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyunseok.android.sharemusicplaylist.adapter.TabPagerAdapter;

/**
 * Search Tab 에서 검색했을 때 나오는 검색 결과를 상세히 보여주는 액티비티
 */
public class SearchActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    LinearLayout linearLayout; // 검색 후 처음 보여주는 이미지
    RelativeLayout relativeLayout; // 검색 후 처음 보여주는 이미지를 클릭하면 상세히 보여줌.
    ViewPager viewPager;
    TextView tv_playlist, tv_track, tv_TAG, tv_album;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setWidget();
        setLayout();
    }

    private void setWidget() {
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tabLayout);
        linearLayout = findViewById(R.id.linearLayout);
        relativeLayout = findViewById(R.id.relativeLayout);
        viewPager = findViewById(R.id.viewPager);

        tv_playlist = findViewById(R.id.tv_playlist);
        tv_track = findViewById(R.id.tv_track);
        tv_TAG = findViewById(R.id.tv_TAG);
        tv_album = findViewById(R.id.tv_album);
        tv_playlist.setOnClickListener(this::goDetail);
        tv_track.setOnClickListener(this::goDetail);
        tv_TAG.setOnClickListener(this::goDetail);
        tv_album.setOnClickListener(this::goDetail);
    }

    private void setLayout() {
        //toolbar.setTitle("ToolbarHAHA");
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

        linearLayout.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);

        tabLayout.setVisibility(View.INVISIBLE);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // Tab 생성 및 타이틀 입력         //.setIcon(R.mipmap.ic_launcher) // icon 추가가능
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_playlist_search)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_tracks)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_TAG)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_album)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager
        TabPagerAdapter adapter = new TabPagerAdapter(getSupportFragmentManager());
        // 프래그먼트 생성 및 어댑터에 추가 // 첫번째 인자는 열의 개수, 두 번째 인자는 Tab의 flag
        adapter.add(SearchTabFragment.newInstance(SearchTabFragment.TYPE_PLAYLIST)); // Playlist 보기 형식
        adapter.add(SearchTabFragment.newInstance(SearchTabFragment.TYPE_TRACK)); // Tracks 곡 보기 형식
        adapter.add(SearchTabFragment.newInstance(SearchTabFragment.TYPE_TAG)); // TAG 보기 형식
        adapter.add(SearchTabFragment.newInstance(SearchTabFragment.TYPE_ALBUM)); // Albums 보기 형식

        viewPager.setAdapter(adapter);
        // 1. 페이저가 변경되었을 때 탭을 바꿔주는 리스너(Pager Listener)
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        // 2. 탭이 변경되었을 때 페이지를 바꿔주는 리스너
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public void goDetail(View v) {

        // TODO 각 TAB 별로 알맞는 소스추가
        switch (v.getId()) {
            case R.id.tv_playlist:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_track:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_TAG:
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv_album:
                viewPager.setCurrentItem(3);
                break;
        }

        linearLayout.setVisibility(View.GONE); // 항목 클릭 시 항목 레이아웃 숨김.
        relativeLayout.setVisibility(View.VISIBLE); // 항목 클릭시 자세히보기 레이아웃 보임.
        tabLayout.setVisibility(View.VISIBLE); // 항목 클릭 시 탭레이아웃 보임.
    }

    @Override
    public void onBackPressed() {
        if(linearLayout.getVisibility() == View.GONE) { // Linear 안보일 경우
            linearLayout.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            tabLayout.setVisibility(View.INVISIBLE);
        } else if(linearLayout.getVisibility() == View.VISIBLE) {
            if(relativeLayout.getVisibility() == View.GONE) {
                super.onBackPressed();
            }
        }
    }
}
