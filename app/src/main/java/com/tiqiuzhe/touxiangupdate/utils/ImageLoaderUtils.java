package com.tiqiuzhe.touxiangupdate.utils;

/**
 * ===================================================================================
 * 
 * @作者: lzh
 * 
 * @创建时间: 2014年9月17日 下午6:24:06
 * 
 * @描述: ImageLoader加载图片
 * 
 * @修改时间: 
 * 
 * ====================================================================================
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tiqiuzhe.touxiangupdate.R;

import java.io.File;

public class ImageLoaderUtils {

	private static ImageLoaderUtils imageLoaderUtils;
	private static DisplayImageOptions options;
	private static ImageLoader imageLoader;
	private static File cacheDir;
	public static ImageLoaderUtils newInstance(){
		if(imageLoaderUtils==null){
			imageLoaderUtils = new ImageLoaderUtils();
			imageLoader = ImageLoader.getInstance();
			config();
		}
		return imageLoaderUtils; 
	}
	/**
	 * 
	 */     
	@SuppressWarnings("deprecation")
	private static void config(){
		options = new DisplayImageOptions.Builder()
		//		.showImageOnLoading(R.drawable.icon_normal)    // 设置图片在下载期间显示的图片 
		.showImageForEmptyUri(R.mipmap.icon_normal)  // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.mipmap.icon_normal)       // 设置图片加载或解码过程中发生错误显示的图片
		//		.cacheInMemory(true)				   // 设置下载的图片是否缓存在内存中  
		.cacheOnDisc(true)							   // 设置下载的图片是否缓存在SD卡中
		.bitmapConfig(Bitmap.Config.RGB_565)           //设置图片的解码类型
		//		.displayer(new FadeInBitmapDisplayer(100))     //是否图片加载好后渐入的动画时间 
//		.resetViewBeforeLoading(true)				   //设置图片在下载前是否重置，复位  
		.considerExifParams(true)					   //是否考虑JPEG图像EXIF参数（旋转，翻转）
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT) //设置图片以如何的编码方式显示,EXACTLY_STRETCHED:图片会缩放到目标大小完全   IN_SAMPLE_INT图像将被二次采样的整数倍
		.build();
	}

	@SuppressWarnings("deprecation")
	public void initImageLoader(Context context) {
		cacheDir = StorageUtils.getOwnCacheDirectory(context, "junyi/Cache");  //设置缓存路径
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		.threadPriority(Thread.NORM_PRIORITY - 2)
		.threadPoolSize(3)										    //线程池内加载的数量
//		.memoryCacheExtraOptions(720, 1280)                     // 保存的每个缓存文件的最大长宽  
		.memoryCache(new WeakMemoryCache())
		.memoryCacheSize(2 * 1024 * 1024)
		.discCacheSize(50 * 1024 * 1024)
		.imageDownloader(new BaseImageDownloader(context))
		.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
		.tasksProcessingOrder(QueueProcessingType.LIFO)
		.diskCacheSize(50 * 1024 * 1024)
		.diskCacheFileCount(100)    //缓存的文件数量 
		.discCache(new UnlimitedDiscCache(cacheDir))  //自定义缓存路径
		.discCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的时候的URI名称用MD5 加密
		.denyCacheImageMultipleSizesInMemory()
		//				.writeDebugLogs() // Remove for release app
		.build();
		imageLoader.init(config);
	}

	public  void loadImage(String iamgeUrl,ImageView imageView){
		imageLoader.displayImage(iamgeUrl, imageView, options);
	}

	/**
	 * 
	 * @描述:清楚缓存
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void clearCache(){
		imageLoader.clearDiscCache();
	}

	/**
	 * 
	 * @描述 缓存网络图片,用最高色彩模式来设置图片
	 * 
	 */
	public  void loadHeightImage(String imageUrl,ImageView imageView){
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		//		.showImageOnLoading(R.drawable.icon_normal)    // 设置图片在下载期间显示的图片 
		.showImageForEmptyUri(R.mipmap.icon_normal)  // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.mipmap.icon_normal)       // 设置图片加载或解码过程中发生错误显示的图片
		//		.cacheInMemory(true)				   // 设置下载的图片是否缓存在内存中  
		.cacheOnDisc(true)							   // 设置下载的图片是否缓存在SD卡中
		.bitmapConfig(Bitmap.Config.ARGB_8888)           //设置图片的解码类型
		//		.displayer(new FadeInBitmapDisplayer(100))     //是否图片加载好后渐入的动画时间 
//		.resetViewBeforeLoading(true)				   //设置图片在下载前是否重置，复位  
		.considerExifParams(true)					   //是否考虑JPEG图像EXIF参数（旋转，翻转）
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT) //设置图片以如何的编码方式显示,EXACTLY_STRETCHED:图片会缩放到目标大小完全   IN_SAMPLE_INT图像将被二次采样的整数倍
		.build(); 
		imageLoader.displayImage(imageUrl, imageView, options); 
	}
	
	/**
	 * 
	 * @描述:缓存网络图片
	 * 
	 */
	public  void loadCacheImage(String imageUrl,ImageView imageView){
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		//		.cacheInMemory(true)  
		.cacheOnDisc(true)
		//		.showImageOnLoading(R.drawable.icon_normal) 
		.showImageForEmptyUri(R.mipmap.icon_normal)
		.showImageOnFail(R.mipmap.icon_normal)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.build();  
		imageLoader.displayImage(imageUrl, imageView, options); 
	}

	/**
	 * 显示图片的下载进度
	 * */
	public void loadImageProgress(String imageUrl,ImageView imageView){
		ImageLoader.getInstance().displayImage(imageUrl, imageView, options, new SimpleImageLoadingListener(), new ImageLoadingProgressListener() {
			@Override
			public void onProgressUpdate(String imageUri, View view, int current, int total) {
			}  
		}); 
	}


}
