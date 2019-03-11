package com.dizoo.imageload;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView ivPicassoCircle;
    private ImageView ivPicassoRound;
    private ImageView ivGlideCircle;
    private ImageView ivGlideRound;

    private String imageUrl = "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3424457561,879660577&fm=27&gp=0.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPicassoCircle = findViewById(R.id.iv_picasso_circle);
        ivPicassoRound = findViewById(R.id.iv_picasso_round);
        ivGlideCircle = findViewById(R.id.iv_glide_circle);
        ivGlideRound = findViewById(R.id.iv_glide_round);
        initPicassoCircle();
        initPicassoRound();
        initGlideCircle();
        initGlideRound();
    }

    private void initPicassoCircle() {
        Picasso.with(this)
                .load(imageUrl)
                .transform(new PicassoCircleTransForm())
                .into(ivPicassoCircle);
    }

    private void initPicassoRound() {
        Picasso.with(this)
                .load(imageUrl)
                .transform(new PicassoRoundCornerForm(20))
                .into(ivPicassoRound);
    }

    private void initGlideCircle() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).load(imageUrl).apply(options).into(ivGlideCircle);
    }

    private void initGlideRound() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.color.colorAccent)
                .error(R.color.colorAccent)
                //.priority(Priority.HIGH)
                .bitmapTransform(new GlideRoundTransForm(20))
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(this).load(imageUrl).apply(options).into(ivGlideRound);
//        GlideApp.with(this).load(imageUrl).transform(new GlideRoundTransForm(45)).into(ivGlideRound);
    }
}
