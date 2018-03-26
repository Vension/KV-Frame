package com.vension.customview.acceleration_progress;

import android.animation.TypeEvaluator;
import android.util.Log;

/**
 * Created by SongNick on 15/9/7.
 */
public class AccTypeEvaluator implements TypeEvaluator<Float> {

    @Override
    public Float evaluate(float fraction, Float startValue, Float endValue) {
        Log.d("", " current fraction == " + fraction);
        return fraction * (endValue - startValue);
    }

}
