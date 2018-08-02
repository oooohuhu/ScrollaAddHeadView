package com.jh.customscroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private MyScrollView scrollView;

    private View headerView;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        scrollView = (MyScrollView) findViewById(R.id.scrollview);
        headerView = findViewById(R.id.lay_header);
        imgView = (ImageView) findViewById(R.id.civ_avatar);

        headerView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.setHeaderView(headerView, imgView);
            }
        });

    }

}
