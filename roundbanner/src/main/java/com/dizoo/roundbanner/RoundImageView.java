package com.dizoo.roundbanner;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.shehuan.niv.NiceImageView;

public class RoundImageView extends NiceImageView {
    public RoundImageView(Context context) {
        this(context,null);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setCornerRadius(20);
    }
}
