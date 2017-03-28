package com.hyunseok.android.sharemusicplaylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.StackView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    List<String> horizontalList;
    HorizontalAdapter horizontalAdapter;

    @ViewById
    RecyclerView recyclerView_horizon;

    @AfterViews // Define Initialization Code
    protected void init() {
        horizontalList = new ArrayList<>();
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

        horizontalAdapter = new HorizontalAdapter(this, horizontalList);


        LinearLayoutManager horizontalLayoutManager
                = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_horizon.setLayoutManager(horizontalLayoutManager);
        recyclerView_horizon.setAdapter(horizontalAdapter);
    }

    public void button_search(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity_.class);
        startActivity(intent);
        //finish();
    }
}