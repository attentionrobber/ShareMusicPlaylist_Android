package com.hyunseok.android.sharemusicplaylist;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    TextView textView;
    @ViewById
    Button button;

    public void button_test(View view) {
        textView.setText("Is it well?");
    }
}
