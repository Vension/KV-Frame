package com.vension.frame.views;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * 倒计时的TextView
 * Created by Vension on 2017/4/28.
 */

public class TimerTextView extends AppCompatTextView implements Runnable {
    private TimerDownListener _TimerDownListener;

    private long mSecond;
    private boolean isRun = false;//是否启动的状态
    public TimerTextView(Context context) {
        super(context);
    }

    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化倒计时的时间
     * @param times
     */
    public void setTimes(long times){
        mSecond = times;
    }

    /**
     * 倒计时计算
     */
    private void computeTime(){
        mSecond--;
    }

    /**
     * 判断是否在倒计时
     * @return
     */
    public boolean isRun(){
        return isRun;
    }

    /**
     * 开始倒计时
     */
    public void beginRun(){
        this.isRun = true;
        run();
    }

    /**
     * 暂停倒计时
     */
    public void stopRun(){
        this.isRun = false;
    }

    @Override
    public void run() {
        if (isRun){
            computeTime();
            if (mSecond == 0) {
                setText("跳过");
                stopRun();
                if (_TimerDownListener != null){
                    _TimerDownListener.onTimerDown();
                }
                return;
            }
            String strTime =  mSecond +"秒";
            setText(strTime);
            postDelayed(this,1000);
        }else {
            removeCallbacks(this);
        }
    }

    public void setTimerDownListener(TimerDownListener mTimerDownListener){
          this._TimerDownListener = mTimerDownListener;
    }

    public interface TimerDownListener{
        void onTimerDown();
    }

}
