package com.dizoo.roundbanner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.shehuan.niv.NiceImageView;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RoundBanner banner;

    private List<String> bannerList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        banner = findViewById(R.id.banner);
        bannerList.add("https://www.wanandroid.com/blogimgs/00f83f1d-3c50-439f-b705-54a49fc3d90d.jpg");
        bannerList.add("https://www.wanandroid.com/blogimgs/ab17e8f9-6b79-450b-8079-0f2287eb6f0f.png");
        bannerList.add("https://www.wanandroid.com/blogimgs/fb0ea461-e00a-482b-814f-4faca5761427.png");
        banner.setBannerAnimation(Transformer.Default);
        banner.isAutoPlay(true);
        banner.setDelayTime(2000);
        banner.setImages(bannerList);
//        banner.setImageLoader(new RoundImageLoader() {
//            @Override
//            public void displayImage(Context context, Object path, RoundImageView imageView) {
//                Picasso.with(context).load((String) path).into(imageView);
//            }
//        });
        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Picasso.with(context).load((String) path).into(imageView);
            }
        });
        banner.start();
    }
}
