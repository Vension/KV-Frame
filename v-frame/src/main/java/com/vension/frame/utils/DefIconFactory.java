package com.vension.frame.utils;


import com.vension.frame.R;

import java.util.Random;

/**
 * Created by Vension on 2017/03/29.
 * 默认背景工厂类
 */
public final class DefIconFactory {

    private final static int[] DEF_ICON_ID = new int[] {
            R.drawable.ic_default_1,
            R.drawable.ic_default_2,
            R.drawable.ic_default_3,
            R.drawable.ic_default_4,
            R.drawable.ic_default_5
    };
    private final static int[] DEF_COLOR_ID = new int[] {
            R.color.random_color_1,
            R.color.random_color_2,
            R.color.random_color_3,
            R.color.random_color_4,
            R.color.random_color_5,
            R.color.random_color_6,
            R.color.random_color_7,
            R.color.random_color_8,
            R.color.random_color_9,
            R.color.random_color_10,
            R.color.random_color_11
    };

    private static Random sRandom = new Random();

    private DefIconFactory() {
        throw new RuntimeException("DefIconFactory cannot be initialized!");
    }


    public static int provideIcon() {
        int index = sRandom.nextInt(DEF_ICON_ID.length);
        return DEF_ICON_ID[index];
    }

    public static int provideColor() {
        int index = sRandom.nextInt(DEF_COLOR_ID.length);
        return DEF_COLOR_ID[index];
    }

}
