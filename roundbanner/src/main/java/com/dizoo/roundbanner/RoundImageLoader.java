package com.dizoo.roundbanner;

import android.content.Context;

import com.shehuan.niv.NiceImageView;
import com.youth.banner.loader.ImageLoaderInterface;

public abstract class RoundImageLoader implements ImageLoaderInterface<RoundImageView> {

    @Override
    public RoundImageView createImageView(Context context) {
        RoundImageView view = new RoundImageView(context);
        return view;
    }
}
