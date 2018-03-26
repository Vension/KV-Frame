package com.vension.frame.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;

/**
 * Created by Vension on 2017/4/24.
 * 根据文字生成水印
 */
public class WaterMarkTextUtil {

    /**
     * text height
     *
     * @param fontSize  fontsize
     * @return  fontsize `s height
     */
    public static int getFontHeight(float fontSize) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.ascent);
    }


    public static Bitmap drawWaterMark(String text, Context context){
        int textSize=400;
        if(android.text.TextUtils.isEmpty(text)){
            text="助这儿 Android";
        }

        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        float density = metric.density;
        int Dh= getFontHeight(textSize/density)*2;

        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(30);
        paint.setAntiAlias(true); //抗锯齿
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);

        float textWidth = paint.measureText(text);
        float bitmapWidth=textWidth/1.414f;

        Bitmap bitmap = Bitmap.createBitmap((int)textWidth, (int)(2*textWidth), Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Path path = new Path();
        path.moveTo(0, bitmapWidth+Dh);
        path.lineTo(bitmapWidth+Dh, 0);
        canvas.drawTextOnPath(text, path, 0, Dh, paint);

        Path path2 = new Path();
        path2.moveTo(0, bitmapWidth*2+Dh);
        path2.lineTo(bitmapWidth+Dh, bitmapWidth);

        canvas.drawTextOnPath(text, path2, 0, Dh, paint);

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();

        return bitmap;
    }

}