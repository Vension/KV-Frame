package com.vension.customview.radarscanview.scan_1;

import android.graphics.Rect;

import java.util.List;

/**
 * Created by Andy on 2016/3/15.
 */
public class Utils {
    public static final boolean hasViewAt(final Rect rect, final List<Rect> rects){
        for (int i = 0; i < rects.size(); i++) {
            Rect r = rects.get(i);
            if(rect.intersect(r)){
                return true;
            }
        }
        return false;
    }
}
