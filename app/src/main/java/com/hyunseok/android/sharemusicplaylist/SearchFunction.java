package com.hyunseok.android.sharemusicplaylist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hyunseok.android.sharemusicplaylist.adapter.HorizontalAdapter;

import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kang on 2017-04-05.
 */

public class SearchFunction {
    private LinearLayout recommend_layout,searchResult_layout;
    private RecyclerView recyclerView_horizon_Latest;
    private RecyclerView recyclerView_horizon_Best;
    private ImageButton btn_search;
    private EditText et_search;
    View view;
    private Context context;
    private int status = View.GONE;

    public SearchFunction(View view,Context context){
        this.view = view;
        this.context = context;
    }
    public void init_widget(){
        et_search = (EditText)view.findViewById(R.id.et_search);
        recyclerView_horizon_Latest = (RecyclerView)view.findViewById(R.id.recyclerView_horizon_latest);
        recyclerView_horizon_Best = (RecyclerView)view.findViewById(R.id.recyclerView_horizon_best);
        btn_search = (ImageButton)view.findViewById(R.id.imgbtn_search);
        recommend_layout = (LinearLayout)view.findViewById(R.id.recommend_layout);
        searchResult_layout= (LinearLayout)view.findViewById(R.id.result_layout);
    }
    public void init_searchTab() {
        init_widget();
        btn_search.setOnClickListener(clickListener);

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

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_search.getText().toString().equals("")){
                    recommend_layout.setVisibility(View.VISIBLE);
                    searchResult_layout.setVisibility(View.GONE);
                }else{
                    recommend_layout.setVisibility(View.GONE);

                    searchResult_layout.setVisibility(View.VISIBLE);
                    //TODO : RETROFIT & RECYCLERVIEW
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

        if(status == View.GONE)
            status = View.VISIBLE;
        else
            status = View.GONE;

        recommend_layout.setVisibility(status);
    };

}
