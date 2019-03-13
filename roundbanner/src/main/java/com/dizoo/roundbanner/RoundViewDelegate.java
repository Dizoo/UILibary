package com.dizoo.roundbanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

public class RoundViewDelegate {

    private final RectF roundRect = new RectF();
    private float rect_adius = 20;  //单位为像素
    private final Paint maskPaint = new Paint();
    private final Paint zonePaint = new Paint();
    private View mView;
    private Context mContext;

    public RoundViewDelegate(View view, Context context){
        this.mView = view;
        this.mContext = context;
        init(context);
    }

    private void init(Context context){
        maskPaint.setAntiAlias(true);
        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //
        zonePaint.setAntiAlias(true);
        zonePaint.setColor(Color.WHITE);
        rect_adius = dip2px(context,20f);
    }

    /**
     * 从新设置圆角
     * @param adius
     */
    public void setRectAdius(float adius) {
        rect_adius = adius;
        if(mView != null){
            mView.invalidate();
        }
    }

    /**
     * 圆角区域设置
     * @param width
     * @param height
     */
    public void roundRectSet(int width,int height){
        roundRect.set(0, 0, width, height);
    }

    /**
     * 画布区域裁剪
     * @param canvas
     */
    public void canvasSetLayer(Canvas canvas){
        canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
        //
        canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
