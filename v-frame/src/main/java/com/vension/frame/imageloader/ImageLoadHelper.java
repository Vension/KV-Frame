package com.vension.frame.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.vension.frame.BuildConfig;
import com.vension.frame.R;
import com.vension.frame.utils.DefIconFactory;

/**
 * 加载各种图片的URI方式
 String imageUri = "http://site.com/image.png"; // from Web
 String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
 String imageUri = "content://media/external/audio/albumart/13"; // from content provider
 String imageUri = "assets://image.png"; // from assets
 String imageUri = "drawable://" + R.drawable.image; // from drawables (only images, non-9patch)

 如果OutOfMemoryError错误很常见，可以通过下面的方式设置
 (1).减少configuration中线程池的线程数目(.threadPoolSize(...)) 推荐为1 - 5
 (2).display options通过.bitmapConfig(Bitmap.Config.RGB_565)设置. Bitmaps in RGB_565 consume 2 times less memory than in ARGB_8888.
 (3).使用configuration的memoryCache(new WeakMemoryCache())方法 或者不调用.cacheInMemory()方法
 (4).display options通过.imageScaleType(ImageScaleType.IN_SAMPLE_INT) 或者 .imageScaleType(ImageScaleType.EXACTLY)方法
 (4).避免使用RoundedBitmapDisplayer，它创建了一个新的ARGB_8888 Bitmap对象
 * Created by Administrator on 2017/5/4.
 */

public class ImageLoadHelper {

	private static final int MAX_DISK_CACHE = 1024 * 1024 * 50; //50 Mb sd卡(本地)缓存的最大值
	private static final int MAX_MEMORY_CACHE = 1024 * 1024 * 10;// 内存缓存的最大值

	/**
	 * 是否显示日志
	 */
	private static boolean isShowLog = false;

	public static ImageLoader imageLoader;

	/**
	 * 单例构建
	 * @return
	 */
	public static ImageLoader getImageLoader(){
		if(imageLoader==null){
			synchronized (ImageLoadHelper.class){
				imageLoader=ImageLoader.getInstance();
			}
		}
		return imageLoader;
	}

	/**
	 * 在Application中初始化参数
	 * @param context
	 */
	public static void initImageLoader(Context context){
		ImageLoaderConfiguration.Builder build = new ImageLoaderConfiguration.Builder(context);
		build.tasksProcessingOrder(QueueProcessingType.LIFO);
		build.threadPoolSize(Thread.MAX_PRIORITY); // default  线程池内加载的数量
		build.threadPriority(Thread.NORM_PRIORITY - 2);// default 设置当前线程的优先级
		build.diskCacheSize(MAX_DISK_CACHE);//sd卡(本地)缓存的最大值
		build.memoryCacheSize(MAX_MEMORY_CACHE); // 内存缓存的最大值
		build.memoryCache(new LruMemoryCache(MAX_MEMORY_CACHE));//可以通过自己的内存缓存实现
		build.denyCacheImageMultipleSizesInMemory();
		build.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());// default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new Md5FileNameGenerator())加密
//		build.diskCache(new UnlimitedDiskCache(new File(FileCache.getCacheImagePath())));//设置sd卡(本地)缓存的路径
		if (BuildConfig.DEBUG && isShowLog) {
			build.writeDebugLogs();
		}
		getImageLoader().init(build.build());
	}

	/**
	 * 自定义Option
	 * @param url
	 * @param target
	 * @param options
	 */
	public static void loadImage(String url, ImageView target, DisplayImageOptions options) {
		imageLoader.displayImage(url, target, options);
	}

	/**
	 * 头像专用
	 *
	 * @param url
	 * @param target
	 */
	public static void loadHeadIcon(String url, ImageView target) {
		imageLoader.displayImage(url, target, getOptions4Header());

	}

	/**
	 * 图片详情页专用
	 *
	 * @param url
	 * @param target
	 * @param loadingListener
	 */
	public static void loadImageForDetail(String url, ImageView target, SimpleImageLoadingListener loadingListener) {
		imageLoader.displayImage(url, target, getOption4ExactlyType(), loadingListener);
	}

	/**
	 * 图片列表页专用
	 *
	 * @param url
	 * @param target
	 * @param loadingResource
	 * @param loadingListener
	 * @param progressListener
	 */
	public static void loadImageList(String url, ImageView target, int loadingResource, SimpleImageLoadingListener loadingListener, ImageLoadingProgressListener progressListener) {
		imageLoader.displayImage(url, target, getOptions4PictureList(loadingResource), loadingListener, progressListener);
	}

	/**
	 * 自定义加载中图片
	 *
	 * @param url
	 * @param target
	 * @param loadingResource
	 */
	public static void loadImageWithLoadingPicture(String url, ImageView target, int loadingResource) {
		imageLoader.displayImage(url, target, getOptions4PictureList(loadingResource));
	}

	/**
	 * 当使用WebView加载大图的时候，使用本方法现下载到本地然后再加载
	 *
	 * @param url
	 * @param loadingListener
	 */
	public static void loadImageFromLocalCache(String url, SimpleImageLoadingListener loadingListener) {
		imageLoader.loadImage(url, getOption4ExactlyType(), loadingListener);
	}



	/**
	 * 加载头像专用Options，默认加载中、失败和空url为 ic_loading_small
	 *
	 * @return
	 */
	public static DisplayImageOptions getOptions4Header() {
		return new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showImageForEmptyUri(R.mipmap.img_load_empty)
				.showImageOnFail(R.mipmap.img_load_failure)
				.showImageOnLoading(R.mipmap.img_load_empty)
				.build();
	}

	/**
	 * 设置图片放缩类型为模式EXACTLY，用于图片详情页的缩放
	 *
	 * @return
	 */
	public static DisplayImageOptions getOption4ExactlyType() {
		return new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.resetViewBeforeLoading(true)
				.considerExifParams(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.build();
	}

	/**
	 * 加载图片列表专用，加载前会重置View
	 * {@link DisplayImageOptions.Builder#resetViewBeforeLoading} = true
	 *
	 * @param loadingResource
	 * @return
	 */
	public static DisplayImageOptions getOptions4PictureList(int loadingResource) {
		return new DisplayImageOptions.Builder()
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.resetViewBeforeLoading(true)
				.showImageOnLoading(loadingResource)
				.showImageForEmptyUri(loadingResource)
				.showImageOnFail(loadingResource)
				.build();
	}



	static public DisplayImageOptions.Builder builder() {
		return new DisplayImageOptions.Builder()
				//设置图片图片的缩放方式(若出现OOM则变成IN_SAMPLE_INT 或者 EXACTLY)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.showImageOnLoading(DefIconFactory.provideIcon())
				.showImageForEmptyUri(DefIconFactory.provideIcon())
				.showImageOnFail(DefIconFactory.provideIcon())
				//设置图片的解码类型 (若出现OOM则变成RGB_565)
				.bitmapConfig(Bitmap.Config.RGB_565)
				//设置图片在下载前是否重置，复位
//				.resetViewBeforeLoading(true)
				.cacheInMemory(true)
				.cacheOnDisk(true);
	}


	static public DisplayImageOptions getDefaultOptions() {
//		return builder().displayer(new FadeInBitmapDisplayer(200)).build();
		return builder().build();
	}

	static public DisplayImageOptions getRoundedOptions(int cornerRadiusPixels) {
		return builder()
				.bitmapConfig(Bitmap.Config.ARGB_8888)
				.displayer(new RoundedBitmapDisplayer(cornerRadiusPixels))
				.build();
	}

	static public DisplayImageOptions getCircleOptions() {
		return builder()
				.bitmapConfig(Bitmap.Config.ARGB_8888)
				.displayer(new CircleBitmapDisplayer())
				.build();
	}


//	static public double getDiskCacheSize() {
//		return FileCache.getFileOrFilesSize(FileCache.getCachePath(), FileCache.TYPE_MB);
//	}

	static public void clearCacheByUrl(String url) {
		imageLoader.getDiskCache().remove(url);
		imageLoader.getMemoryCache().remove(url);
	}


//	static public void loadResources(ImageView imageAware, int resId) {
//		load(imageAware, "drawable://" + resId, mDisplayImageOptions);
//	}
//
//	static public void loadFile(ImageView imageAware, String path) {
//		load(imageAware, "file://" + path, mDisplayImageOptions);
//	}

}

