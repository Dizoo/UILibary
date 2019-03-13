package com.dizoo.addview;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

public class ProductView extends FrameLayout {

    public ProductView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.product,this,true);
    }
}
