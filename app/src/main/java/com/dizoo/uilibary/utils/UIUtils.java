package com.dizoo.uilibary.utils;

import android.content.Context;
import android.content.res.Resources;

import com.dizoo.uilibary.BaseApplication;


/**
 * UI工具类
 */
public class UIUtils {

    /**
     * 得到上下文
     *
     * @return
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**
     * 得到resources对象
     *
     * @return
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到string.xml中的字符串
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }

    /**
     * 得到string.xml中的字符串，带点位符
     *
     * @return
     */
    public static String getString(int id, Object... formatArgs) {
        return getResource().getString(id, formatArgs);
    }

    /**
     * 得到string.xml中和字符串数组
     *
     * @param resId
     * @return
     */
    public static String[] getStringArr(int resId) {
        return getResource().getStringArray(resId);
    }

    /**
     * 得到colors.xml中的颜色
     * @param colorId
     * @return
     */
    public static int getColor(int colorId) {
        return getResource().getColor(colorId);
    }
}
