package com.dizoo.umengshare;

import android.Manifest;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,123);
        }
        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    private void showShare() {
        new ShareAction(MainActivity.this)
                .setDisplayList( SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE)//设置分享面板显示的平台
                .setShareboardclickCallback(boardListener)//添加之前创建的面板监听器
                .open(config);//开启分享面板 其中可传入能对面板样式进行自定义操作的config对象
        //比如设置面板在底部还是中部显示、是否有取消按钮、图标形状、字体大小和背景颜色等等
    }

    ShareBoardConfig config = new ShareBoardConfig();
    UMWeb web = new UMWeb("https://gank.io/");//创建要分享的Web对象，传入分享的url地址
    ShareBoardlistener boardListener = new ShareBoardlistener() { //创建面板的监听器
        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA platform) {
            new ShareAction(MainActivity.this)//开启分享
                    .withMedia(web) //填入创建好的分享内容
                    .setPlatform(platform)//填入选择的平台
                    .setCallback(new UMShareListener() {
                        @Override
                        public void onStart(SHARE_MEDIA share_media) {
                            toast("onStart");
                        }

                        @Override
                        public void onResult(SHARE_MEDIA share_media) {
                            toast("onResult");
                        }

                        @Override
                        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                            toast("onError");
                        }

                        @Override
                        public void onCancel(SHARE_MEDIA share_media) {
                            toast("onCancel");
                        }
                    })//设置对分享返回结果的监听
                    .share();//启动分享操作
        }
    };

    private void toast(String toast){
        Toast.makeText(MainActivity.this, "..." + toast + "...", Toast.LENGTH_SHORT).show();
    }
}
