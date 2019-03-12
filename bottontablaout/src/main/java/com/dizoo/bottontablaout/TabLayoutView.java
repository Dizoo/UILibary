package com.dizoo.bottontablaout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 底部状态栏
 */
public class TabLayoutView extends LinearLayout {

    private Context mContext;
    //tab标题文字
    private String[] mTabTitles;
    //tab图片资源
    private int[] mTabImages;
    //tab标题大小
    private int mTabTextSize;
    //tab文字未选中颜色
    private int mTextUnselectedColor;
    //tab文字选中颜色
    private int mTextSelectedColor;
    private int mImageWidth;
    private int mImageHeight;
    private List<TextView> mTvList;
    private List<ImageView> mImList;
    private List<TextView> mTvDotList;
    private int mCurrentIndex = 0;
    private boolean needCheck = false;

    private OnItemOnclickListener onItemOnclickListener;

    public TabLayoutView(Context context) {
        this(context, null);
    }

    public TabLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public void setOnItemOnclickListener(OnItemOnclickListener onItemOnclickListener) {
        this.onItemOnclickListener = onItemOnclickListener;
    }

    /**
     * 设置标题、图片和当前选中的条目
     * @param tabTitles
     * @param tabImages
     * @param currentIndex
     */
    public void setDataSource(String[] tabTitles, int[] tabImages, int currentIndex) {
        this.mTabTitles = tabTitles;
        this.mTabImages = tabImages;
        this.mCurrentIndex = currentIndex;
    }

    /**
     * 设置图标大小
     * @param imgwidth
     * @param imgheight
     */
    public void setImageStyle(int imgwidth, int imgheight) {
        this.mImageWidth = dip2px(mContext, imgwidth);
        this.mImageHeight = dip2px(mContext, imgheight);
    }

    /**
     * 设置标题颜色
     * @param tabTestSize
     * @param textUnselectedColor
     * @param textSelectedColor
     */
    public void setTextStyle(int tabTestSize, int textUnselectedColor, int textSelectedColor) {
        this.mTabTextSize = tabTestSize;
        this.mTextUnselectedColor = textUnselectedColor;
        this.mTextSelectedColor = textSelectedColor;
    }

    /**
     * 动态布局
     * 1、外层为横向线下布局
     * 2、动态添加相对布局，平分父布局，使宽度一致，添加到横向布局中
     * 3、总线布局添加图标和标题，并添加到相对布局中
     * 4、添加圆点到相对布局中，并设置在3的右上角
     */
    public void initDatas() {
        mTvList = new ArrayList<>();
        mImList = new ArrayList<>();
        mTvDotList = new ArrayList<>();

        setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutParams.gravity = Gravity.CENTER;

        LayoutParams imageLayoutParams = new LayoutParams(mImageWidth, mImageHeight);
        imageLayoutParams.gravity = Gravity.CENTER;

        LayoutParams textLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textLayoutParams.gravity = Gravity.CENTER;

        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        final int size = mTabTitles.length;
        for (int i = 0; i < size; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(imageLayoutParams);
            if (null == mTabImages) {
                imageView.setVisibility(GONE);
            } else {
                imageView.setImageResource(mTabImages[i]);
            }
            TextView textView = new TextView(mContext);
            textView.setText(mTabTitles[i]);
            textView.setLayoutParams(textLayoutParams);
            textView.setTextSize(mTabTextSize);
            textView.setPadding(0, 5, 0, 0);

            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setId(i + 100);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setOrientation(VERTICAL);
            linearLayout.setLayoutParams(imageLayoutParams);
            linearLayout.addView(imageView);

            RelativeLayout relativeLayout = new RelativeLayout(mContext);

            RelativeLayout.LayoutParams dotLayoutParams = new RelativeLayout.LayoutParams(dip2px(mContext, 15), dip2px(mContext, 15));
            dotLayoutParams.addRule(RelativeLayout.RIGHT_OF, linearLayout.getId());
            dotLayoutParams.addRule(RelativeLayout.ABOVE, linearLayout.getId());
            dotLayoutParams.setMargins(-dip2px(mContext, 8), 0, 0, -dip2px(mContext, 10));

            TextView tvDot = new TextView(mContext);
            tvDot.setText("0");
            tvDot.setTextSize(8);
            tvDot.setGravity(Gravity.CENTER);
            tvDot.setVisibility(GONE);
            //设置小圆点
//            tvDot.setTextColor(mContext.getResources().getColor( R.color.color_white));
//            tvDot.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.tab_dot));

            final int index = i;
            relativeLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemOnclickListener != null) {
                        if (needCheck()) {
                            onItemOnclickListener.onNeedOhterMether(index);
                        } else {
                            setSelectStyle(index);
                            onItemOnclickListener.onItemClick(index);
                        }
                    }
                }
            });

            linearLayout.addView(textView);
            relativeLayout.addView(linearLayout, relativeLayoutParams);
            relativeLayout.addView(tvDot, dotLayoutParams);
            addView(relativeLayout, layoutParams);

            mTvList.add(textView);
            mImList.add(imageView);
            mTvDotList.add(tvDot);
        }
        setSelectStyle(mCurrentIndex);
    }

    public void setSelectStyle(int index) {
        int size = mTabTitles.length;
        for (int i = 0; i < size; i++) {
            if (i == index) {
                mTvList.get(i).setTextColor(mContext.getResources().getColor(mTextSelectedColor));
                mImList.get(i).setSelected(true);
            } else {
                mTvList.get(i).setTextColor(mContext.getResources().getColor(mTextUnselectedColor));
                mImList.get(i).setSelected(false);
            }
        }
    }

    /**
     * 设置圆点
     *
     * @param index 圆点索引
     * @param count 圆点个数
     */
    public void setDotsCount(int index, int count) {
        if (mTvDotList == null || index > mTvDotList.size() - 1) {
            return;
        }
        if (count > 0) {
            mTvDotList.get(index).setVisibility(VISIBLE);
            if (count > 99) {
                mTvDotList.get(index).setText(99 + "+");
            } else {
                mTvDotList.get(index).setText(count + "");
            }
        } else {
            mTvDotList.get(index).setVisibility(GONE);
        }
    }

    public void setNeedCheck(boolean needCheck){
        this.needCheck = needCheck;
    }

    private boolean needCheck() {
        return needCheck;
    }

    public interface OnItemOnclickListener {
        void onItemClick(int index);

        void onNeedOhterMether(int index);
    }

    private int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    private int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
