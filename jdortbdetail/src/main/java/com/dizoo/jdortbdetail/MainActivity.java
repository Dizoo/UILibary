package com.dizoo.jdortbdetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SlideDetailsLayout slide;
    private ScrollView scroll;
    private TextView tvEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slide = findViewById(R.id.slide);
        scroll = findViewById(R.id.scroll);
        tvEnter = findViewById(R.id.tv_enter);
        tvEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slide.smoothOpen(true);
            }
        });
    }
}
