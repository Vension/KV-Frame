package com.vension.frame.views.nine_imageview;

import java.io.Serializable;

public class ImageAttr implements Serializable {

    public String url;
    public String thumbnailUrl;

    // 显示的宽高
    public int width;
    public int height;

    // 真实的高度
    public int realWidth;
    public int realHeight;

    // 左上角坐标
    public int left;
    public int top;

    public int type;//0：图片 1：视频

    @Override
    public String toString() {
        return "ImageAttr{" +
                "width=" + width +
                ", height=" + height +
                ", realWidth=" + realWidth +
                ", realHeight=" + realHeight +
                ", left=" + left +
                ", top=" + top +
                ", type=" + type +
                '}';
    }
}
