package com.dizoo.bottontablaout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class TabLayout extends LinearLayout implements View.OnClickListener {

    private ArrayList<Tab> tabs;
    private OnTabClickListener listener;
    private View selectView;
    private int tabCount;
    private boolean isLogin = false;

    public TabLayout(Context context) {
        super(context);
        setUpView();
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    private void setUpView() {
        setOrientation(HORIZONTAL);
    }

    public void setUpData(ArrayList<Tab> tabs, OnTabClickListener listener) {
        this.tabs = tabs;
        this.listener = listener;

        if (tabs != null && tabs.size() > 0) {
            tabCount = tabs.size();
            TabView mTabView;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.weight = 1;
            for (int i = 0; i < tabs.size(); i++) {
                mTabView = new TabView(getContext());
                mTabView.setTag(tabs.get(i));
                mTabView.setUpData(tabs.get(i));
                mTabView.setOnClickListener(this);
                addView(mTabView, params);
            }
        } else {
            throw new IllegalArgumentException("tabs can't be empty");
        }
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public ArrayList<Tab> getData() {
        return tabs;
    }

    public void setCurrentTab(int i) {
        if (i < tabCount && i >= 0) {
            View view = getChildAt(i);
            onClick(view);
        }
    }

    public void onDataChanged(int i, int badgeCount) {
        if (i < tabCount && i >= 0) {
            TabView view = (TabView) getChildAt(i);
            view.onDataChanged(badgeCount);
        }
    }

    @Override
    public void onClick(View v) {
        if (selectView != v) {
            listener.onTabClick((Tab) v.getTag());
            v.setSelected(true);
            if (selectView != null) {
                selectView.setSelected(false);
            }
            selectView = v;
        }
    }

    public interface OnTabClickListener {
        void onTabClick(Tab tab);
    }

    public class TabView extends LinearLayout {
        private ImageView mTabImg;
        private TextView mTabText;

        public TabView(Context context) {
            super(context);
            setUpView();
        }

        public TabView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setUpView();
        }

        public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setUpView();
        }

        private void setUpView() {
            LayoutInflater.from(getContext()).inflate(R.layout.view_tab, this, true);
            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER);
            mTabImg = (ImageView) findViewById(R.id.img_tab_icon);
            mTabText = (TextView) findViewById(R.id.txt_tab_name);
        }

        public void setUpData(Tab tab) {
            mTabImg.setBackgroundResource(tab.imgResId);
            mTabText.setText(tab.labelResId);
        }

        public void onDataChanged(int badgeCount) {
            //  TODO notify new message, change the badgeView
        }
    }

    public static class Tab {
        public int imgResId;
        public int labelResId;
        public int badgeCount;
        public Class<? extends TabFragment> targetFragmentClz;

        public Tab(int imgResId, int labelResId) {
            this.imgResId = imgResId;
            this.labelResId = labelResId;
        }

        public Tab(int imgResId, int labelResId, int badgeCount) {
            this.imgResId = imgResId;
            this.labelResId = labelResId;
            this.badgeCount = badgeCount;
        }

        public Tab(int imgResId, int labelResId, Class<? extends TabFragment> targetFragmentClz) {
            this.imgResId = imgResId;
            this.labelResId = labelResId;
            this.targetFragmentClz = targetFragmentClz;
        }
    }
}
