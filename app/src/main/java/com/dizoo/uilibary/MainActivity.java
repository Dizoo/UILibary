package com.dizoo.uilibary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dizoo.uilibary.adapter.HorizontalRvAdapter;
import com.dizoo.uilibary.view.HorizontalLayoutManager;
import com.dizoo.uilibary.view.MarqueeView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "UILibary";
    private RecyclerView mHorizontalRv;
    private MarqueeView mMarqueeView;
    private List<String> horizontalRvList = new ArrayList<>();
    private List<String> marqueeViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initData();
        initRv();
        initMarqueeView();
    }

    private void initMarqueeView() {
        mMarqueeView.startWithList(marqueeViewList);
        // 在代码里设置自己的动画
        mMarqueeView.startWithList(marqueeViewList, R.anim.anim_bottom_in, R.anim.anim_top_out);
        mMarqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                Toast.makeText(MainActivity.this,
                        String.valueOf(mMarqueeView.getPosition()) + ". " + textView.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initRv() {
        HorizontalLayoutManager horizontalLayoutManager = new HorizontalLayoutManager(this,HorizontalLayoutManager.HORIZONTAL,false);
        horizontalLayoutManager.setChangeAlpha(true);
        horizontalLayoutManager.setScaleDownBy(0.99f);
        horizontalLayoutManager.setScaleDownDistance(0.8f);
        HorizontalRvAdapter horizontalRvAdapter = new HorizontalRvAdapter(R.layout.horizontal_rv_item,horizontalRvList);
        SnapHelper snapHelper = new LinearSnapHelper();
        if (mHorizontalRv.getOnFlingListener() == null) {
            snapHelper.attachToRecyclerView(mHorizontalRv);
        }
        mHorizontalRv.setLayoutManager(horizontalLayoutManager);
        mHorizontalRv.setAdapter(horizontalRvAdapter);
        mHorizontalRv.smoothScrollToPosition(horizontalRvAdapter.getItemCount() -1);
        horizontalLayoutManager.setOnScrollStopListener(new HorizontalLayoutManager.onScrollStopListener() {
            @Override
            public void selectedView(View view, int index) {
                TextView horizontalTv = (TextView) view;
                Log.d(TAG, "selectedView: " + horizontalTv.getText() + "..." + index);
//                Toast.makeText(MainActivity.this, horizontalTv.getText() +
//                        "..." + index, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViewById() {
        mHorizontalRv = findViewById(R.id.horizontal_rv);
        mMarqueeView = findViewById(R.id.marqueeView);
    }

    private void initData() {
        //横向RecycleView的数据
        for (int i = 0; i < 15; i++) {
            horizontalRvList.add("条目：" + i);
        }
        //横向广告的数据
        for (int i = 0; i < 5; i++) {
            marqueeViewList.add("滚动的广告条目：" + i);
        }
    }

}
