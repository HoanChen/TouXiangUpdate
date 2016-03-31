package com.tiqiuzhe.touxiangupdate;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.tiqiuzhe.touxiangupdate.utils.ImageLoaderUtils;

import java.io.File;

/**
 * ============================================================================ =======
 * 
 * @作者: lzh
 * 
 * @创建时间: 2014年12月17日 下午4:52:43
 * 
 * @描述: android程序入口点初始化一些信息，设置一些全局变量
 * 
 * @修改时间:
 * 
 * ====================================================================== ==============
 */

public class CommApplication extends Application {
	public static Context context;
	public static DisplayMetrics displayMetrics;
	/** 缓存路径 */
	private static String cacheDir;
	private static final String TAG = "Application";
	private static CommApplication instance;
	public static CommApplication getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		instance = this;
		displayMetrics = getResources().getDisplayMetrics();
		displayMetrics = getResources().getDisplayMetrics();
		initCacheDirPath();
		initImageLoader();
	}

	/**
	 * 初始化缓存路径
	 */
	private void initCacheDirPath() {
		File f;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			f = new File(Environment.getExternalStorageDirectory() + "/comm/");
			if (!f.exists()) {
				f.mkdir();
			}
		} else {
			f = getApplicationContext().getCacheDir();
		}
		cacheDir = f.getAbsolutePath();
	}
	
	/**
	 * 
	 * 初始化ImageLoder
	 * 
	 * */
	private void initImageLoader() {
		ImageLoaderUtils.newInstance().initImageLoader(this);
	}

	public static String getCacheDirPath() {
		return cacheDir;
	}




	/**
	 * 清空全部的sharePreference中的信息
	 * @param context
	 */
	public static void clearLoginInfo(Context context) {

//		SharePrefUtil.clear(context);

		// 注销极光
		// JPushManager.newInstence(context).setAlias("");
		// JPushManager.newInstence(context).stopJPush();
	}

}
