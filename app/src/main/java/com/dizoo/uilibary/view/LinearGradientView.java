package com.dizoo.uilibary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dizoo.uilibary.R;

/**
 * 上下渐变色
 */
public class LinearGradientView extends View {

    private int mDiraction = 0;
    private Paint paint;

    public LinearGradientView(Context context) {
        this(context,null);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 获取 View 的宽高
        int width = getWidth();
        int height = getHeight();

        int colorStart = getResources().getColor(R.color.colorAccent);
        int colorEnd = getResources().getColor(R.color.colorPrimary);


        LinearGradient backGradient = null;
        if (mDiraction == 0){
            backGradient = new LinearGradient(0, 0, 0, height, new int[]{colorStart ,colorEnd}, null, Shader.TileMode.CLAMP);
        }else {
            backGradient =new LinearGradient(0, height, width, 0, colorStart ,colorEnd, Shader.TileMode.CLAMP);
        }

        paint.setShader(backGradient);
        canvas.drawRect(0, 0, width, height, paint);

    }

    /**
     * 设置渐变方向
     * @param direction 0 表示 上下渐变，1 表示 45°渐变
     */
    public void setDirection(int direction){
        mDiraction = direction;
    }
}
