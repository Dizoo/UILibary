package com.dizoo.uilibary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {

    private ToastUtil(){}

    public static Toast toast = null;

    public static void showToast(Context context , String msg) {
        if (context == null) {
            return;
        }
        if (context instanceof Activity) {
            context = context.getApplicationContext();
        }
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        }else{
            toast.setText(msg);
        }
        toast.show();
    }
}
