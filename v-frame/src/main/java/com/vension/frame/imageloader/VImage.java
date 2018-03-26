package com.vension.frame.imageloader;


import android.widget.ImageView;

public class VImage implements ImageLoader {

    private static ImageLoader imageLoader;
    private static VImage vImage;

    public static void init(ImageLoader loader) {
        imageLoader = loader;
    }

    public static VImage getInstance() {
        if (imageLoader==null){
            throw new NullPointerException("Call XFrame.initXImageLoader(ImageLoader loader) within your Application onCreate() method." +
                    "Or extends XApplication");
        }
        if (vImage == null) {
            vImage = new VImage();
        }
        return vImage;
    }

    @Override
    public void load(ImageView imageView, Object imageUrl) {
        imageLoader.load(imageView, imageUrl);
    }

    @Override
    public void load(ImageView imageView, Object imageUrl, int defaultImage) {
        imageLoader.load(imageView, imageUrl, defaultImage);
    }

    @Override
    public void load(ImageView imageView, Object imageUrl, Object transformation) {
        imageLoader.load(imageView, imageUrl, transformation);
    }

}
