package com.vension.frame.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by long on 2016/9/27.
 * 动画帮助类
 */
public final class AnimateHelper {

    private AnimateHelper() {
        throw new RuntimeException("AnimateHelper cannot be initialized!");
    }

    /**
     * 旋转动画
     * @param view  视图
     * @param duration  时间
     * @param repeatCount  -1代表无限循环
     *
     * Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);
    LinearInterpolator lin = new LinearInterpolator();
    operatingAnim.setInterpolator(lin);
    if (operatingAnim != null) {
    view.startAnimation(operatingAnim);
    }
     */
    public static void rotate(View view, int duration, int repeatCount) {
        RotateAnimation rotate  = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(duration);//设置动画持续时间
        rotate.setRepeatCount(repeatCount);//设置重复次数
        view.startAnimation(rotate);
    }

    /**
     * 闪烁缩放动画
     *
     * @param view 视图
     * @param duration  动画时间
     *
     */
    @SuppressLint("WrongConstant")
    public static AnimatorSet getAlphaAndScaleAnimatorSet(View view, int duration) {

        //第一种实现方式：
        /*Animation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(duration);
		alphaAnimation.setInterpolator(new LinearInterpolator());
		alphaAnimation.setRepeatCount(Animation.INFINITE);
		alphaAnimation.setRepeatMode(Animation.REVERSE);
        view.startAnimation(alphaAnimation);
        //创建缩放动画对象
        Animation animation = new ScaleAnimation(0, 1.0f, 0f, 1.0f);
        animation.setDuration(1500);//动画时间
        animation.setRepeatCount(3);//动画的重复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        imageView1.startAnimation(animation);//开始动画*/

        //第二种实现方式：
        /*AnimatorSet set = new AnimatorSet();
        set.playTogether(
                (ObjectAnimator.ofFloat(view, "alpha", 1.0f, 1.4f, 0.9f, 1.0f)),
                ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.4f, 0.9f, 1.0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.4f, 0.9f, 1.0f)
        );
        set.setDuration(duration);
        set.start();*/

        //第三种实现方式
        List<Animator> animators = new ArrayList<>();
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(view, "alpha", 0.1f, 1.2f, 1.0f);
        alphaAnim.setDuration(duration);
        alphaAnim.setRepeatCount(ValueAnimator.INFINITE);//无限循环
        alphaAnim.setRepeatMode(ValueAnimator.INFINITE);//
        animators.add(alphaAnim);

        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.2f, 1.0f);
        scaleXAnim.setDuration(duration);
        scaleXAnim.setRepeatCount(ValueAnimator.INFINITE);
        scaleXAnim.setRepeatMode(ValueAnimator.INFINITE);
        animators.add(scaleXAnim);

        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(view, "scaleY", 0.8f, 1.2f, 1.0f);
        scaleYAnim.setDuration(duration);
        scaleYAnim.setRepeatCount(ValueAnimator.INFINITE);
        scaleYAnim.setRepeatMode(ValueAnimator.INFINITE);
        animators.add(scaleYAnim);

        AnimatorSet _AnimatorSet = new AnimatorSet();
        _AnimatorSet.playTogether(animators);
        _AnimatorSet.setStartDelay(10);
        return _AnimatorSet;
    }

    /**
     * 心跳动画
     * @param view  视图
     * @param duration  时间
     */
    public static void doHeartBeat(View view, int duration) {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 1.4f, 0.9f, 1.0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 1.4f, 0.9f, 1.0f)
        );
        set.setDuration(duration);
        set.start();
    }

    /**
     * 跳跃动画
     * @param view  视图
     * @param jumpHeight 跳跃高度
     * @param duration  时间
     * @return  Animator
     */
    public static Animator doHappyJump(View view, int jumpHeight, int duration) {
        Keyframe scaleXFrame1 = Keyframe.ofFloat(0f, 1.0f);
        Keyframe scaleXFrame2 = Keyframe.ofFloat(0.05f, 1.5f);
        Keyframe scaleXFrame3 = Keyframe.ofFloat(0.1f, 0.8f);
        Keyframe scaleXFrame4 = Keyframe.ofFloat(0.15f, 1.0f);
        Keyframe scaleXFrame5 = Keyframe.ofFloat(0.5f, 1.0f);
        Keyframe scaleXFrame6 = Keyframe.ofFloat(0.55f, 1.5f);
        Keyframe scaleXFrame7 = Keyframe.ofFloat(0.6f, 0.8f);
        Keyframe scaleXFrame8 = Keyframe.ofFloat(0.65f, 1.0f);
        PropertyValuesHolder scaleXHolder = PropertyValuesHolder.ofKeyframe("scaleX",
                scaleXFrame1, scaleXFrame2, scaleXFrame3, scaleXFrame4,
                scaleXFrame5, scaleXFrame6, scaleXFrame7, scaleXFrame8);

        Keyframe scaleYFrame1 = Keyframe.ofFloat(0f, 1.0f);
        Keyframe scaleYFrame2 = Keyframe.ofFloat(0.05f, 0.5f);
        Keyframe scaleYFrame3 = Keyframe.ofFloat(0.1f, 1.15f);
        Keyframe scaleYFrame4 = Keyframe.ofFloat(0.15f, 1.0f);
        Keyframe scaleYFrame5 = Keyframe.ofFloat(0.5f, 1.0f);
        Keyframe scaleYFrame6 = Keyframe.ofFloat(0.55f, 0.5f);
        Keyframe scaleYFrame7 = Keyframe.ofFloat(0.6f, 1.15f);
        Keyframe scaleYFrame8 = Keyframe.ofFloat(0.65f, 1.0f);
        PropertyValuesHolder scaleYHolder = PropertyValuesHolder.ofKeyframe("scaleY",
                scaleYFrame1, scaleYFrame2, scaleYFrame3, scaleYFrame4,
                scaleYFrame5, scaleYFrame6, scaleYFrame7, scaleYFrame8);

        Keyframe translationY1 = Keyframe.ofFloat(0f, 0f);
        Keyframe translationY2 = Keyframe.ofFloat(0.085f, 0f);
        Keyframe translationY3 = Keyframe.ofFloat(0.2f, -jumpHeight);
        Keyframe translationY4 = Keyframe.ofFloat(0.25f, -jumpHeight);
        Keyframe translationY5 = Keyframe.ofFloat(0.375f, 0f);
        Keyframe translationY6 = Keyframe.ofFloat(0.5f, 0f);
        Keyframe translationY7 = Keyframe.ofFloat(0.585f, 0f);
        Keyframe translationY8 = Keyframe.ofFloat(0.7f, -jumpHeight);
        Keyframe translationY9 = Keyframe.ofFloat(0.75f, -jumpHeight);
        Keyframe translationY10 = Keyframe.ofFloat(0.875f, 0f);
        PropertyValuesHolder translationYHolder = PropertyValuesHolder.ofKeyframe("translationY",
                translationY1, translationY2, translationY3, translationY4, translationY5,
                translationY6, translationY7, translationY8, translationY9, translationY10);

        Keyframe rotationY1 = Keyframe.ofFloat(0f, 0f);
        Keyframe rotationY2 = Keyframe.ofFloat(0.125f, 0f);
        Keyframe rotationY3 = Keyframe.ofFloat(0.3f, -360f * 3);
        PropertyValuesHolder rotationYHolder = PropertyValuesHolder.ofKeyframe("rotationY",
                rotationY1, rotationY2, rotationY3);

        Keyframe rotationX1 = Keyframe.ofFloat(0f, 0f);
        Keyframe rotationX2 = Keyframe.ofFloat(0.625f, 0f);
        Keyframe rotationX3 = Keyframe.ofFloat(0.8f, -360f * 3);
        PropertyValuesHolder rotationXHolder = PropertyValuesHolder.ofKeyframe("rotationX",
                rotationX1, rotationX2, rotationX3);

        ValueAnimator valueAnimator = ObjectAnimator.ofPropertyValuesHolder(view,
                scaleXHolder, scaleYHolder, translationYHolder, rotationYHolder, rotationXHolder);
        valueAnimator.setDuration(duration);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.start();
        return valueAnimator;
    }

    /**
     * 裁剪视图宽度
     * @param view
     * @param srcHeight
     * @param endHeight
     * @param duration
     */
    public static void doClipViewHeight(final View view, int srcHeight, int endHeight, int duration) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(srcHeight, endHeight).setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int width = (int) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = width;
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }

    /**
     * 垂直偏移动画
     * @param view
     * @param startY
     * @param endY
     * @param duration
     * @return
     */
    public static Animator doMoveVertical(View view, int startY, int endY, int duration) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", startY, endY).setDuration(duration);
        animator.start();
        return animator;
    }

    /**
     * 动画是否在运行
     * @param animator
     */
    public static boolean isRunning(Animator animator) {
        return animator != null && animator.isRunning();
    }

    /**
     * 启动动画
     * @param animator
     */
    public static void startAnimator(Animator animator) {
        if (animator != null && !animator.isRunning()) {
            animator.start();
        }
    }

    /**
     * 停止动画
     * @param animator
     */
    public static void stopAnimator(Animator animator) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }

    /**
     * 删除动画
     * @param animator
     */
    public static void deleteAnimator(Animator animator) {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
        animator = null;
    }

    static ObjectAnimator invisToVis;
    static ObjectAnimator visToInvis;


    public interface OnUpdateListener {
        void onUpdate(int intValue);
    }
    /**
     * 颜色渐变动画
     *
     * @param beforeColor 变化之前的颜色
     * @param afterColor  变化之后的颜色
     * @param listener    变化事件
     */
    public static void animationColorGradient(int beforeColor, int afterColor, final OnUpdateListener listener) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new ArgbEvaluator(), beforeColor, afterColor).setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                textView.setTextColor((Integer) animation.getAnimatedValue());
                listener.onUpdate((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    /**
     * 卡片翻转动画
     *
     * @param beforeView
     * @param AfterView
     */
    public static void cardFilpAnimation(final View beforeView, final View AfterView) {
        Interpolator accelerator = new AccelerateInterpolator();
        Interpolator decelerator = new DecelerateInterpolator();
        if (beforeView.getVisibility() == View.GONE) {
            // 局部layout可达到字体翻转 背景不翻转
            invisToVis = ObjectAnimator.ofFloat(beforeView,
                    "rotationY", -90f, 0f);
            visToInvis = ObjectAnimator.ofFloat(AfterView,
                    "rotationY", 0f, 90f);
        } else if (AfterView.getVisibility() == View.GONE) {
            invisToVis = ObjectAnimator.ofFloat(AfterView,
                    "rotationY", -90f, 0f);
            visToInvis = ObjectAnimator.ofFloat(beforeView,
                    "rotationY", 0f, 90f);
        }

        visToInvis.setDuration(250);// 翻转速度
        visToInvis.setInterpolator(accelerator);// 在动画开始的地方速率改变比较慢，然后开始加速
        invisToVis.setDuration(250);
        invisToVis.setInterpolator(decelerator);
        visToInvis.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationEnd(Animator arg0) {
                if (beforeView.getVisibility() == View.GONE) {
                    AfterView.setVisibility(View.GONE);
                    invisToVis.start();
                    beforeView.setVisibility(View.VISIBLE);
                } else {
                    AfterView.setVisibility(View.GONE);
                    visToInvis.start();
                    beforeView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator arg0) {

            }

            @Override
            public void onAnimationRepeat(Animator arg0) {

            }

            @Override
            public void onAnimationStart(Animator arg0) {

            }
        });
        visToInvis.start();
    }

    /**
     * 缩小动画
     *
     * @param view
     */
    public static void zoomIn(final View view, float scale, float dist) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -dist);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    /**
     * 放大动画
     *
     * @param view
     */
    public static void zoomOut(final View view, float scale) {
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();

        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", view.getTranslationY(), 0);

        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    public static void ScaleUpDowm(View view) {
        ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(1200);
        view.startAnimation(animation);
    }

    public static void animateHeight(int start, int end, final View view) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(start, end);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();//根据时间因子的变化系数进行设置高度
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);//设置高度
            }
        });
        valueAnimator.start();
    }

    public static ObjectAnimator popup(final View view, final long duration) {
        view.setAlpha(0);
        view.setVisibility(View.VISIBLE);

        ObjectAnimator popup = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat("alpha", 0f, 1f),
                PropertyValuesHolder.ofFloat("scaleX", 0f, 1f),
                PropertyValuesHolder.ofFloat("scaleY", 0f, 1f));
        popup.setDuration(duration);
        popup.setInterpolator(new OvershootInterpolator());

        return popup;
    }

    public static ObjectAnimator popout(final View view, final long duration, final AnimatorListenerAdapter animatorListenerAdapter) {
        ObjectAnimator popout = ObjectAnimator.ofPropertyValuesHolder(view,
                PropertyValuesHolder.ofFloat("alpha", 1f, 0f),
                PropertyValuesHolder.ofFloat("scaleX", 1f, 0f),
                PropertyValuesHolder.ofFloat("scaleY", 1f, 0f));
        popout.setDuration(duration);
        popout.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationEnd(animation);
                }
            }
        });
        popout.setInterpolator(new AnticipateOvershootInterpolator());

        return popout;
    }



    class Rotate3d extends Animation {
        private float mCenterX = 0;
        private float mCenterY = 0;

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            mCenterX = width/2;
            mCenterY = height/2;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Matrix matrix = t.getMatrix();
            Camera camera = new Camera();
            camera.save();
            camera.rotateY(720 * interpolatedTime);
            camera.getMatrix(matrix);
            camera.restore();
            matrix.preTranslate(-mCenterX, -mCenterY);
            matrix.postTranslate(mCenterX, mCenterY);
        }
    }

}
