package com.hyunseok.android.sharemusicplaylist;

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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_search)
public class SearchActivity extends AppCompatActivity {

    @ViewById
    Toolbar toolbar;
    @ViewById
    TabLayout tabLayout;
    @ViewById
    LinearLayout linearLayout;
    @ViewById
    RelativeLayout relativeLayout;
    @ViewById
    ViewPager viewPager;
    @ViewById
    TextView tv_playlist, tv_track, tv_TAG, tv_album;

    @AfterViews
    protected void init() {
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

    @Click({R.id.imgbtn_search})
    public void search() {
        Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
    }

    @Click({R.id.tv_playlist, R.id.tv_track, R.id.tv_TAG, R.id.tv_album})
    public void goDetail(View v) {

        // TODO 각 TAB 별로 알맞는 소스추가
        switch (v.getId()) {
            case R.id.tv_playlist:
                //Toast.makeText(this, "playlist", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_track:
                //Toast.makeText(this, "track", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_TAG:
                //Toast.makeText(this, "TAG", Toast.LENGTH_SHORT).show();
                viewPager.setCurrentItem(2);
                break;
            case R.id.tv_album:
                //Toast.makeText(this, "album", Toast.LENGTH_SHORT).show();
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
        }

        else if(linearLayout.getVisibility() == View.VISIBLE) {
            if(relativeLayout.getVisibility() == View.GONE) {
                super.onBackPressed();
            }
        }
    }
}
