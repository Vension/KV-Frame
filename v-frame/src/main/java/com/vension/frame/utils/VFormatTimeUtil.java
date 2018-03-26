package com.vension.frame.utils;

/**
 * 用来计算显示的时间是多久之前的！
 */

public class VFormatTimeUtil {

    /**
     * 秒与毫秒的倍数
     */
    public static final long SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final long MIN = SEC * 60;
    /**
     * 时与毫秒的倍数
     */
    public static final long HOUR = MIN * 60;
    /**
     * 天与毫秒的倍数
     */
    public static final long DAY = HOUR * 24;

    /**
     * 周与毫秒的倍数
     */
    public static final long WEEK = DAY * 7;

    /**
     * 月与毫秒的倍数
     */
    public static final long MONTH = DAY * 30;

    /**
     * 年与毫秒的倍数
     */
    public static final long YEAR = DAY * 365;

    /**
     * 默认格式
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";


    private VFormatTimeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }
    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis  开始时间戳
     * @return
     */
    public static String getTimeSpanByNow1(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 1000) {
            return "刚刚";
        }else if (span < MIN) {
            return String.format("%d秒前", span /  SEC);
        }else if (span <  HOUR) {
            return String.format("%d分钟前", span /  MIN);
        }else if (span < DAY) {
            return String.format("%d小时前", span /  HOUR);
        }else if (span < WEEK) {
            return String.format("%d天前", span /  DAY);
        }else if (span < MONTH) {
            return String.format("%d周前", span /  WEEK);
        }else if (span < YEAR) {
            return String.format("%d月前", span /  MONTH);
        }else {
            return String.format("%d年前", span /  YEAR);
        }
    }


    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis  开始时间戳
     * @return
     */
    public static String getTimeSpanByNow2(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        long day = span /DAY;
        if (day == 0) {// 今天
            long hour = span / HOUR;
            if(hour <=4){
                return String.format("凌晨%tR", millis);
            }else if(hour >4 && hour <=6){
                return String.format("早上%tR", millis);
            }else if(hour >6 && hour <=11){
                return String.format("上午%tR", millis);
            }else if(hour >11 && hour <=13){
                return String.format("中午%tR", millis);
            }else if(hour >13 && hour <=18){
                return String.format("下午%tR", millis);
            }else if(hour >18 && hour <=19){
                return String.format("傍晚%tR", millis);
            }else if(hour >19 && hour <=24){
                return String.format("晚上%tR", millis);
            }else{
                return String.format("今天%tR", millis);
            }
        } else if (day == 1) {// 昨天
            return String.format("昨天%tR", millis);
        } else if (day == 2) {// 前天
            return String.format("前天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }


}
