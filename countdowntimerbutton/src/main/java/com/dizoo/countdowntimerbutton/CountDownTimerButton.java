package com.dizoo.countdowntimerbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.util.Locale;

public class CountDownTimerButton extends AppCompatButton {

    /**
     * 默认时间间隔1000ms
     */
    private static final long DEFAULT_INTERVAL = 1000;
    /**
     * 默认时长60s
     */
    private static final long DEFAULT_COUNT = 60 * 1000;
    /**
     * 默认倒计时文字格式(显示秒数)
     */
    private static final String DEFAULT_COUNT_FORMAT = "%d";
    /**
     * 默认按钮文字 {@link #getText()}
     */
    private String mDefaultText;
    /**
     * 倒计时时长，单位为毫秒
     */
    private long mCount;
    /**
     * 时间间隔，单位为毫秒
     */
    private long mInterval;
    private int normalColor;
    private int countDownColor;
    /**
     * 倒计时文字格式
     */
    private String mCountDownFormat = DEFAULT_COUNT_FORMAT;
    /**
     * 倒计时是否可用
     */
    private boolean mEnableCountDown = true;
    /**
     * 点击事件监听器
     */
    private OnClickListener onClickListener;

    /**
     * 倒计时
     */
    private CountDownTimer mCountDownTimer;

    /**
     * 是否正在执行倒计时
     */
    private boolean isCountDownNow;
    private FinishCallback finishCallback;

    public CountDownTimerButton(Context context) {
        super(context);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CountDownTimerButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 获取自定义属性值
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CountDownButton);
        mCountDownFormat = typedArray.getString(R.styleable.CountDownButton_countDownFormat);
        if (typedArray.hasValue(R.styleable.CountDownButton_countDown)) {
            mCount = (int) typedArray.getFloat(R.styleable.CountDownButton_countDown, DEFAULT_COUNT);
        }
        mInterval =
                (int) typedArray.getFloat(R.styleable.CountDownButton_countDownInterval, DEFAULT_INTERVAL);
        mEnableCountDown =
                (mCount > mInterval) && typedArray.getBoolean(R.styleable.CountDownButton_enableCountDown,
                        true);
        countDownColor =
                typedArray.getColor(R.styleable.CountDownButton_countDownColor, 0);
        normalColor =
                typedArray.getColor(R.styleable.CountDownButton_normalColor, 0);
        mDefaultText = getText().toString();
        typedArray.recycle();
        // 初始化倒计时Timer
        if (mCountDownTimer == null) {
            mCountDownTimer = new CountDownTimer(mCount, mInterval) {
                @Override public void onTick(long millisUntilFinished) {
                    long currentCount = millisUntilFinished / 1000;
                    if (currentCount == 60) {
                        finishCallback.countDownTimerFinish();
                    }
                    setText(String.format(Locale.CHINA, mCountDownFormat, currentCount));
                    setTextColor(countDownColor);
                }

                @Override public void onFinish() {
                    isCountDownNow = false;
                    setEnabled(true);
                    setText(mDefaultText);
                    setTextColor(normalColor);
                }
            };
        }
    }

    @Override public void setOnClickListener(OnClickListener onClickListener) {
        super.setOnClickListener(onClickListener);
        this.onClickListener = onClickListener;
    }

    public void startAction() {
        manualStart();
    }

    @Override public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
        }
        return super.onTouchEvent(event);
    }

    public void setEnableCountDown(boolean enableCountDown) {
        this.mEnableCountDown = (mCount > mInterval) && enableCountDown;
    }

    public void setCountDownFormat(String countDownFormat) {
        this.mCountDownFormat = countDownFormat;
    }

    public void setCount(long count) {
        this.mCount = count;
    }

    public void setInterval(long interval) {
        mInterval = interval;
    }

    /**
     * 是否正在执行倒计时
     *
     * @return 倒计时期间返回true否则返回false
     */
    public boolean isCountDownNow() {
        return isCountDownNow;
    }

    /**
     * 设置倒计时数据
     *
     * @param count 时长
     * @param interval 间隔
     * @param countDownFormat 文字格式
     */
    public void setCountDown(long count, long interval, String countDownFormat) {
        this.mCount = count;
        this.mCountDownFormat = countDownFormat;
        this.mInterval = interval;
        setEnableCountDown(true);
    }

    /**
     * 移除倒计时
     */
    public void removeCountDown() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        isCountDownNow = false;
        setText(mDefaultText);
        setEnabled(true);
    }

    @Override public void setEnabled(boolean enabled) {
        if (isCountDownNow()) {
            return;
        }
        super.setEnabled(enabled);
        setClickable(enabled);
    }

    public void setFinishCallback(FinishCallback finishCallback) {
        this.finishCallback = finishCallback;
    }

    public interface FinishCallback {
        void countDownTimerFinish();
    }

    public void manualStart() {
        mDefaultText = getText().toString();
        // 设置按钮不可点击
        setEnabled(false);
        // 开始倒计时
        mCountDownTimer.start();
        isCountDownNow = true;
    }
}
