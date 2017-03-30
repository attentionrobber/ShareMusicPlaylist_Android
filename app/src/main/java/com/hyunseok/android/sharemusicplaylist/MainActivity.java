package com.hyunseok.android.sharemusicplaylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hyunseok.android.sharemusicplaylist.adapter.HorizontalAdapter;
import com.hyunseok.android.sharemusicplaylist.domain.User;
import com.hyunseok.android.sharemusicplaylist.util.Logger;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
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
    @ViewById
    EditText et_search;
    @ViewById
    ImageButton imgbtn_search;
    
    @ViewById
    Button btnLogin;
    @ViewById
    TextView textUserName;

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

        //receive User information form Login Activity
        try {
            textUserName.setText(((User) getIntent().getSerializableExtra("user")).getName());
        }catch (Exception e){Logger.print("Main Activity", "NO User information in Intent");}

    }
    @Click({R.id.btnLogin})
    public void moveLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.print("MainActivity","onActivityResult===================================================");
    }

    @Click({R.id.imgbtn_search})
    public void search() {
        Intent intent = new Intent(MainActivity.this, SearchActivity_.class);
        startActivity(intent);
        //finish();
    }

}