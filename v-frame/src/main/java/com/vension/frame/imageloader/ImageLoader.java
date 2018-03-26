package com.vension.frame.imageloader;

import android.widget.ImageView;


/**
 * @author ：Created by vension on 2018/1/24.
 * @email：kevin-vension@foxmail.com
 * @desc character determines attitude, attitude determines destiny
 *
 * 框架全局图片加载接口
 */

public interface ImageLoader {

    /**
     * 图片加载方法
     *
     * （默认图片在实现类中实现，此方法主要是全局调用，默认图片统一，避免每次都要传入默认图片）
     *
     * @param imageView
     * @param imageUrl 类型为Object原因：因为你的图片链接可以是string、uri、file等多中类型
     */
    void load(ImageView imageView, Object imageUrl);

    /**
     * 图片加载方法
     *
     * （默认图片可以自己每次单独设置，主要满足软件一些地方可能默认图片不一样的情况）
     *
     * @param imageView
     * @param imageUrl
     * @param defaultImage
     */
    void load(ImageView imageView, Object imageUrl, int defaultImage);

    /**
     * 加载不同形状图片
     * @param imageView
     * @param imageUrl
     * @param transformation 传入你要加载的图片形状实现类
     */
    void load(ImageView imageView, Object imageUrl, Object transformation);
}
