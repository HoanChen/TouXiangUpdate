package com.tiqiuzhe.touxiangupdate;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.os.Handler;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tiqiuzhe.touxiangupdate.bean.LifeItemData;
import com.tiqiuzhe.touxiangupdate.utils.ImageLoaderUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

	private static Handler handler;
	private static int mainThreadId;
	private static Thread mainThread;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		instance = this;
		displayMetrics = getResources().getDisplayMetrics();
		displayMetrics = getResources().getDisplayMetrics();
		initCacheDirPath();
		initImageLoader();
		//初始化
		Fresco.initialize(getApplicationContext());

		//Handler对象
		handler = new Handler();
		//Context
		context = getApplicationContext();
		//主线程id,获取当前方法运行线程id,此方法运行在主线程中,所以获取的是主线程id
		mainThreadId = android.os.Process.myTid();
		//主线程对象
		mainThread = Thread.currentThread();
	}

	public static Handler getHandler() {
		return handler;
	}
	public static Context getContext() {
		return context;
	}
	public static int getMainThreadId() {
		return mainThreadId;
	}
	public static Thread getMainThread() {
		return mainThread;
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

	public static List<LifeItemData> getPos() {
		return pos;
	}

	public static void setPos(List<LifeItemData> pos) {
		CommApplication.pos = pos;
	}

	public static List<LifeItemData> pos=new ArrayList<LifeItemData>();


}
