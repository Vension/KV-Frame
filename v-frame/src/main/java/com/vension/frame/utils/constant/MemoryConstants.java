package com.vension.frame.utils.constant;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *     @author Administrator : vension
 *     @github : https://com.github.vension
 *     @date : Created by long on 2017/10/23.
 *     @desc  : 存储相关常量
 */
public final class MemoryConstants {

    /******************** 存储相关常量 ********************/
    /**
     * Byte与Byte的倍数
     */
    public static final int BYTE = 1;
    /**
     * KB与Byte的倍数
     */
    public static final int KB = 1024;
    /**
     * MB与Byte的倍数
     */
    public static final int MB = 1048576;
    /**
     * GB与Byte的倍数
     */
    public static final int GB = 1073741824;


    @IntDef({BYTE, KB, MB, GB})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Unit {
    }


    public enum MemoryUnit {
        BYTE,
        KB,
        MB,
        GB
    }

}
