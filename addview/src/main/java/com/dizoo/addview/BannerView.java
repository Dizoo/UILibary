package com.dizoo.addview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class BannerView{

    public void init(Context context, LinearLayout container) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner,container,false);
        container.addView(view);
    }
}
