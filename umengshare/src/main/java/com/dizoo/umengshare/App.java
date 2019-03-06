package com.dizoo.umengshare;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, "5c7ddf9e0cafb2d80600129a", "Umeng", UMConfigure.DEVICE_TYPE_PHONE,
                "");
        UMShareAPI.get(this);
    }

    {
        //QQ
        PlatformConfig.setQQZone("101558412", "66547919b5668780f285d8829f593380");
    }
}
