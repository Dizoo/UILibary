package com.dizoo.popwindow;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout parent;
    private CommonPopupWindow commonPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = findViewById(R.id.parent);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               new PopwindowUtils.PopupWindowBuilder(MainActivity.this)
                       .setView(R.layout.pop)
                       .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                       .setBgDarkAlpha(0.7f) // 控制亮度
                       .create()
                       .showAsDropDown(parent,0,20);
            }
        });
    }

    //https://github.com/pinguo-zhouwei/CustomPopwindow
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//        if (commonPopupWindow != null) {
//            if (commonPopupWindow.getPopupWindow() != null && commonPopupWindow.getPopupWindow().isShowing()) {
//                return false;
//            }
//        }
//        return super.dispatchTouchEvent(event);
//
//    }
//
//
//    private void show() {
//        commonPopupWindow = new CommonPopupWindow(this, R.layout.pop, ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT) {
//            @Override
//            protected void initView() {
//                View view = getContentView();
//            }
//
//            @Override
//            protected void initWindow() {
//                super.initWindow();
//                PopupWindow instance = getPopupWindow();
//                instance.setOnDismissListener(new PopupWindow.OnDismissListener() {
//                    @Override
//                    public void onDismiss() {
//                        WindowManager.LayoutParams lp = getWindow().getAttributes();
//                        lp.alpha = 1.0f;
//                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                        getWindow().setAttributes(lp);
//                    }
//                });
//            }
//        };
//        PopupWindow popupWindow = commonPopupWindow.getPopupWindow();
//        popupWindow.setAnimationStyle(R.style.animScale);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.5f;
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        getWindow().setAttributes(lp);
//    }
}
