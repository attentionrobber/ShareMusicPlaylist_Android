package com.hyunseok.android.sharemusicplaylist;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
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

        tabLayout.setVisibility(View.INVISIBLE);
        //tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // Tab 생성 및 타이틀 입력         //.setIcon(R.mipmap.ic_launcher) // icon 추가가능
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_playlist)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_tracks)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_TAG)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_album)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // ViewPager

    }

    @Click({R.id.imgbtn_search})
    public void search() {
        Toast.makeText(this, "search", Toast.LENGTH_SHORT).show();
    }

    @Click({R.id.tv_playlist, R.id.tv_track, R.id.tv_TAG, R.id.tv_album})
    public void goDetail(View v) {

        switch (v.getId()) {
            case R.id.tv_playlist:
                Toast.makeText(this, "playlist", Toast.LENGTH_SHORT).show();
                // TODO case 별로 알맞는 소스추가
                break;
            case R.id.tv_track:
                Toast.makeText(this, "track", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_TAG:
                Toast.makeText(this, "TAG", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_album:
                Toast.makeText(this, "album", Toast.LENGTH_SHORT).show();
                break;
        }

        linearLayout.setVisibility(View.GONE); // 항목 클릭 시 항목 레이아웃 숨김.
        relativeLayout.setVisibility(View.VISIBLE); // 항목 클릭시 자세히보기 레이아웃 보임.
        tabLayout.setVisibility(View.VISIBLE); // 항목 클릭 시 탭레이아웃 보임.


    }

    @Override
    public void onBackPressed() {


        super.onBackPressed();
    }
}
