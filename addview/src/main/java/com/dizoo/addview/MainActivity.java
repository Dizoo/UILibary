package com.dizoo.addview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private TextView banner;
    private TextView product;
    private TextView mine;
    private TextView remove;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        banner = findViewById(R.id.banner);
        product = findViewById(R.id.product);
        mine = findViewById(R.id.mine);
        remove = findViewById(R.id.remove);
        container = findViewById(R.id.container);
//        container.addView(new BannerView(MainActivity.this));
        banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.banner,container,false);
//                container.addView(view);
                BannerView bannerView = new BannerView();
                bannerView.init(MainActivity.this, container);
            }
        });
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.addView(new ProductView(MainActivity.this));
            }
        });
        mine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MineView child = new MineView(MainActivity.this);
                container.addView(child);
            }
        });
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container.removeAllViews();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        container.removeAllViews();
    }
}
