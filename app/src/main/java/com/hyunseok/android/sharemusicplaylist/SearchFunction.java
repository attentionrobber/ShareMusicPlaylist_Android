package com.hyunseok.android.sharemusicplaylist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyunseok.android.sharemusicplaylist.adapter.HorizontalAdapter;
import com.hyunseok.android.sharemusicplaylist.domain.TrackData;
import com.hyunseok.android.sharemusicplaylist.util.Logger;
import com.hyunseok.android.sharemusicplaylist.util.RetrofitDeezerUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kang on 2017-04-05.
 */

// TODO 현재 영문일 경우 어절 단위로 검색이 됨. => alphabet 단위로 검색으로 수정하기.

public class SearchFunction {
    private LinearLayout recommend_layout,searchResult_layout;
    private RecyclerView recyclerView_horizon_Latest,recyclerView_horizon_Best;
    private RecyclerView rcy_Search_Track, rcy_Search_Album;
    private EditText et_search;
    private TextView txtTrack, txtAlbum;
    private ImageButton btnSearch;
    View view;
    private Context context;
    private int status = View.GONE;
    private List<TrackData> track_Datas;
    RetrofitDeezerUtil deezerUtil;

    public SearchFunction(View view,Context context){
        this.view = view;
        this.context = context;
    }
    public void init_widget(){
        btnSearch = (ImageButton)view.findViewById(R.id.imgbtn_search);
        txtTrack = (TextView)view.findViewById(R.id.txtTrack);
        txtAlbum= (TextView)view.findViewById(R.id.txtAlbum);
        et_search = (EditText)view.findViewById(R.id.et_search);
        recyclerView_horizon_Latest = (RecyclerView)view.findViewById(R.id.recyclerView_horizon_latest);
        recyclerView_horizon_Best = (RecyclerView)view.findViewById(R.id.recyclerView_horizon_best);
        recommend_layout = (LinearLayout)view.findViewById(R.id.recommend_layout);
        searchResult_layout= (LinearLayout)view.findViewById(R.id.result_layout);
        rcy_Search_Track = (RecyclerView)view.findViewById(R.id.rcy_Search_Track);
        rcy_Search_Album = (RecyclerView)view.findViewById(R.id.rcy_Search_Album);

        btnSearch.setOnClickListener(clickListener);


    }
    void recyclerView_inittest(){
        List<String> horizontalList = new ArrayList<>();
        horizontalList.add("horizontal 1");
        horizontalList.add("horizontal 2");
        horizontalList.add("horizontal 3");
        horizontalList.add("horizontal 4");
        horizontalList.add("horizontal 5");
        horizontalList.add("horizontal 6");
        horizontalList.add("horizontal 7");
        horizontalList.add("horizontal 8");
        horizontalList.add("horizontal 9");
        horizontalList.add("horizontal 10");

        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(context, horizontalList);
        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        HorizontalAdapter horizontalAdapter1 = new HorizontalAdapter(context, horizontalList);
        LinearLayoutManager horizontalLayoutManager1
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        recyclerView_horizon_Latest.setLayoutManager(horizontalLayoutManager);
        recyclerView_horizon_Latest.setAdapter(horizontalAdapter);

        recyclerView_horizon_Best.setLayoutManager(horizontalLayoutManager1);
        recyclerView_horizon_Best.setAdapter(horizontalAdapter1);
    }
    public void init_searchTab() {
        init_widget();
        recyclerView_inittest();
        deezerUtil = new RetrofitDeezerUtil(context,view);
        deezerUtil.setRetrofit();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (et_search.getText().toString().equals("")) {
                    recommend_layout.setVisibility(View.VISIBLE);
                    searchResult_layout.setVisibility(View.GONE);
                } else {
                    recommend_layout.setVisibility(View.GONE);
                    searchResult_layout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {

            }

        });
    }

    View.OnClickListener clickListener = v -> {
        switch (v.getId()){
            case R.id.imgbtn_search:
                deezerUtil.searchTrack(et_search.getText().toString());
                //deezerUtil.searchList(et_search.getText().toString());
                //Logger.print("SearchFucntion", "search Track Title ==========================" + et_search.getText().toString());
                break;

        }
    };

}

