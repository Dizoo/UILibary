package com.dizoo.uilibary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 屏幕适配工具类
 */
public class WindowUtils {


    public static final int color = Color.parseColor("#78A3FF");
    private static View statusView;

    /**
     *  获取状态栏高度
     * @param a
     * @return
     */
    public static int getStateBarHeight(Activity a) {
        int result = 0;
        int resourceId = a.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = a.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     *  设置图片顶到状态栏
     * @param activity
     */
    public static void setPicTranslucentToStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            //设置透明状态栏,这样才能让 ContentView 向上
            window.addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor( Color.TRANSPARENT);
            //为了设置全屏
            ViewGroup mContentView = (ViewGroup)activity.findViewById( Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                //注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 使其不为系统 View 预留空间.
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
        }
    }

    /**
     * 使状态栏透明
     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 生成一个和状态栏大小相同的矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @return 状态栏矩形条
     */
    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        // 绘制一个和状态栏一样高的矩形
        View statusView = new View (activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,
                statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundResource(color);
        return statusView;
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //取消状态栏
//            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // 生成一个状态栏大小的矩形
            statusView = createStatusView(activity, color);
            // 添加 statusView 到布局中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * 隐藏状态栏view
     */
    public static void hideStatus(){
        if (statusView != null){
            statusView.setVisibility( View.GONE);
        }
    }

    /**
     * 显示状态栏view
     */
    public static void showStatus(){
        if (statusView != null){
            statusView.setVisibility( View.VISIBLE);
        }
    }

    /**
     * 为DrawerLayout 布局设置状态栏变色
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     * @param color        状态栏颜色值
     */
    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 生成一个状态栏大小的矩形
            View statusBarView = createStatusView(activity, color);
            // 添加 statusBarView 到布局中
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.addView(statusBarView, 0);
            // 内容布局不是 LinearLayout 时,设置padding top
            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStateBarHeight(activity), 0, 0);
            }
            // 设置属性
            ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
            drawerLayout.setFitsSystemWindows(false);
            contentLayout.setFitsSystemWindows(false);
            contentLayout.setClipToPadding(true);
            drawer.setFitsSystemWindows(false);
        }
    }

    /**
     * 为 DrawerLayout 布局设置状态栏透明
     *
     * @param activity     需要设置的activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置内容布局属性
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.setFitsSystemWindows(true);
            contentLayout.setClipToPadding(true);
            // 设置抽屉布局属性
            ViewGroup vg = (ViewGroup) drawerLayout.getChildAt(1);
            vg.setFitsSystemWindows(false);
            // 设置 DrawerLayout 属性
            drawerLayout.setFitsSystemWindows(false);
        }
    }

    /**
     *  判断是否有导航栏 并且遮挡
     * @param context
     * @param view
     */
    public static void setNavigationBar(Context context, View view) {
        Resources resources = context.getResources();
        // 用来拿到导航栏的高度
        int resourceId1 = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        // 用来拿到是否有虚拟导航栏
        int resourceId2 = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId2 != 0) {

            // 判断手机是否有虚拟的导航栏
            boolean hasNav = resources.getBoolean(resourceId2);
            // 如果手机有虚拟导航栏（即导航栏是在屏幕上，跟布局重叠在一起，而不是在手机屏幕外的地方）
            if (hasNav) {
                //获取NavigationBar的高度(获取底部导航栏的高度)
                int height = resources.getDimensionPixelSize(resourceId1);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = height;
                view.setLayoutParams(layoutParams);
            }
            // 如果手机没有虚拟导航栏（导航栏在屏幕外，但是导航栏仍然是有的。）
            else {
                view.setVisibility( View.GONE);
            }
        }
    }

    /**
     *  设置状态栏透明
     * @param activity
     */
    @TargetApi(19)
    public static void transparencyBar(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = activity.getWindow();
//            window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//            window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor( Color.TRANSPARENT);
//            window.setNavigationBarColor( Color.TRANSPARENT);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = activity.getWindow();
//            window.setFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
//                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //      getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
//5.0 全透明实现
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView()
                    .setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(
                    Color.TRANSPARENT);// calculateStatusColor(Color.WHITE, (int) alphaValue)
        }
    }

    /**
     *状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     * @param activity
     * @return 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int StatusBarLightMode(Activity activity){
        int result=0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(MIUISetStatusBarLightMode(activity, true)){
                result=1;
            }else if(FlymeSetStatusBarLightMode(activity.getWindow(), true)){
                result=2;
            }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result=3;
            }
        }
        return result;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格
     * 可以用来判断是否为Flyme用户
     * @param window 需要设置的窗口
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     * @param activity
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     * @return  boolean 成功执行返回true
     *
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window=activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if(dark){
                    extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//状态栏透明且黑色字体
                }else{
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result=true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if(dark){
                        activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    }else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            }catch (Exception e){

            }
        }
        return result;
    }

    /**
     * 设置跟布局距离状态栏的间距
     * @param v   跟布局view
     * @param top 状态栏的高度
     */
    public  static void setMargins (View v, int top) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(0, top, 0, 0);
            v.requestLayout();
        }
    }

}
