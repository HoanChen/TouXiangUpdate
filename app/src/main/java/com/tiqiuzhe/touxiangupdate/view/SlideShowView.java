package com.tiqiuzhe.touxiangupdate.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.tiqiuzhe.touxiangupdate.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ViewPager实现的轮播图广告自定义视图，如京东首页的广告轮播图效果； 既支持自动轮播页面也支持手势滑动切换页面
 * 
 * 
 */

public class SlideShowView extends FrameLayout {

	// 使用universal-image-loader插件读取网络图片，需要工程导入universal-image-loader-1.8.6-with-sources.jar
	private ImageLoader imageLoader = ImageLoader.getInstance();

	// 自定义轮播图的资源
	private String[] imageUrls;
	// 放轮播图片的ImageView 的list
	private List<ImageView> imageViewsList;
	// 放圆点的View的list
	private List<View> dotViewsList;

	private ViewPager viewPager;
	// 当前轮播页
	private int currentItem = 0;
	// 定时任务
	private ScheduledExecutorService scheduledExecutorService;

	private Context context;
	private int position = Gravity.CENTER;
	private boolean isRepeat = true;
	private int time = 3000;// 轮播时间间隔
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			currentItem++;
			if (currentItem >= imageViewsList.size()) {
				currentItem = 0;
			}
			if (currentItem >= imageViewsList.size()) {
				currentItem = imageViewsList.size() - 1;
			}
			viewPager.setCurrentItem(currentItem);
			if (isRepeat) {
				handler.sendEmptyMessageDelayed(0, time);
			}
		}
	};
	private OnItemClickListener onItemClickListener;
	public void setOnItemClickListener(OnItemClickListener onItemClickListener){
		this.onItemClickListener = onItemClickListener;
	}
	public interface OnItemClickListener{
		public void onItemClick(int position);
	}
	public SlideShowView(Context context) {
		this(context, null);
		this.context = context;
		initImageLoader(context);
	}

	public SlideShowView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
		this.context = context;
		initImageLoader(context);
	}

	public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;

		initImageLoader(context);

	}

	/**
	 * 开始轮播图切换
	 */
	private void startPlay() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 4,
				TimeUnit.SECONDS);
	}

	/**
	 * 停止轮播图切换
	 */
	private void stopPlay() {
		scheduledExecutorService.shutdown();
	}
	
	/**
	 * 设置小点的位置，Gravity.LEFT,Gravity.CENTER,Gravity.RIGHT
	 */
	public void setGravity(int position){
		this.position = position;
	}
	/**
	 *  自动轮播启用开关
	 * @param isAutoPlay
	 */
	public void isAutoPlay(boolean isAutoPlay){
		this.isRepeat = isAutoPlay;
	}
	/**
	 * 设置图片轮播间隔时间，单位毫秒
	 * @param time
	 */
	public void setAutoTime(int time){
		this.time = time;
	}
	/**
	 * 初始化图数组，传入图片url数组
	 */
	public void initData(String[] imageUrls) {
		imageViewsList = new ArrayList<ImageView>();
		dotViewsList = new ArrayList<View>();
		this.imageUrls = imageUrls;

		initUI(context);

		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		currentItem = 0;
		viewPager.setCurrentItem(currentItem);
		setDotSelect(currentItem);
		if (isRepeat)
			handler.sendEmptyMessageDelayed(0, time);
	}

	/**
	 * 初始化Views等UI
	 */
	private void initUI(Context context) {
		if (imageUrls == null || imageUrls.length == 0)
			return;

		LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this,
				true);

		LinearLayout dotLayout = (LinearLayout) findViewById(R.id.dotLayout);
		dotLayout.setGravity(position);
		dotLayout.removeAllViews();
		// 热点个数与图片特殊相等
		for (int i = 0; i < imageUrls.length; i++) {
			ImageView view = new ImageView(context);
			view.setTag(imageUrls[i]);
			if (i == 0)// 给一个默认图
				view.setBackgroundResource(R.mipmap.appmain_subject_1);
			view.setScaleType(ScaleType.FIT_XY);
			imageViewsList.add(view);

			ImageView dotView = new ImageView(context);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			params.leftMargin = 10;
			params.rightMargin = 10;
			dotLayout.addView(dotView, params);
			dotViewsList.add(dotView);
		}

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setFocusable(true);

		viewPager.setAdapter(new MyPagerAdapter());
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		
	}

	/**
	 * 填充ViewPager的页面适配器
	 * 
	 */
	private class MyPagerAdapter extends PagerAdapter {

		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			// ((ViewPag.er)container).removeView((View)object);
			((ViewPager) container).removeView(imageViewsList.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			ImageView imageView = imageViewsList.get(position);
			
//			imageView.setScaleType(ScaleType.FIT_XY);
//			LinearLayout.LayoutParams params =
//					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//							LinearLayout.LayoutParams.MATCH_PARENT);
//			imageView.setLayoutParams(params);

			imageLoader.displayImage(imageView.getTag() + "", imageView);

			((ViewPager) container).addView(imageViewsList.get(position));
			final int f_position = position;
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (onItemClickListener != null)
						onItemClickListener.onItemClick(f_position);
				}
			});
			return imageViewsList.get(position);
		}

//		@Override
//		public Object instantiateItem(View container, int position) {
//			ImageView imageView = imageViewsList.get(position);
//
//			//			imageView.setScaleType(ScaleType.FIT_XY);
//			//			LinearLayout.LayoutParams params =
//			//					new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//			//							LinearLayout.LayoutParams.MATCH_PARENT);
//			//			imageView.setLayoutParams(params);
//
//			imageLoader.displayImage(imageView.getTag() + "", imageView);
//
//			((ViewPager) container).addView(imageViewsList.get(position));
//			final int f_position = position;
//			imageView.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					if(onItemClickListener!=null)onItemClickListener.onItemClick(f_position);
//				}
//			});
//			return imageViewsList.get(position);
//		}


		@Override
		public int getCount() {
			return imageViewsList.size();

		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}

	}

	/**
	 * ViewPager的监听器 当ViewPager中页面的状态发生改变时调用
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {

		boolean isAutoPlay = true;

		@Override
		public void onPageScrollStateChanged(int arg0) {
			switch (arg0) {
			case 1:// 手势滑动，空闲中
				isAutoPlay = false;
				break;
			case 2:// 界面切换中
				isAutoPlay = true;
				break;
			case 0:// 滑动结束，即切换完毕或者加载完毕
					// 当前为最后一张，此时从右向左滑，则切换到第一张
				if (viewPager.getCurrentItem() == viewPager.getAdapter()
						.getCount() - 1 && !isAutoPlay) {
					currentItem = 0;
					viewPager.setCurrentItem(0);
				}
				// 当前为第一张，此时从左向右滑，则切换到最后一张
				else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
					currentItem = viewPager.getAdapter().getCount() - 1;
					viewPager
							.setCurrentItem(viewPager.getAdapter().getCount() - 1);
				}
				break;
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int pos) {

			currentItem = pos;
			setDotSelect(currentItem);
		}

	}
	
	private void setDotSelect(int currentItem){
		for (int i = 0; i < dotViewsList.size(); i++) {
			if (i == currentItem) {
				((View) dotViewsList.get(currentItem))
						.setBackgroundResource(R.mipmap.img_red);
			} else {
				((View) dotViewsList.get(i))
						.setBackgroundResource(R.mipmap.img_white);
			}
		}
	}

	// /**
	// *执行轮播图切换任务
	// *
	// */
	private class SlideShowTask implements Runnable {

		@Override
		public void run() {
			synchronized (viewPager) {
				currentItem = (currentItem + 1) % imageViewsList.size();
				handler.obtainMessage().sendToTarget();
			}
		}

	}

	/**
	 * 销毁ImageView资源，回收内存
	 * 
	 */
	private void destoryBitmaps() {

		for (int i = 0; i < imageViewsList.size(); i++) {
			ImageView imageView = imageViewsList.get(i);
			Drawable drawable = imageView.getDrawable();
			if (drawable != null) {
				// 解除drawable对view的引用
				drawable.setCallback(null);
			}
		}
	}


//	/**
//	 * 配置ImageLoder
//	 */
//	private void configImageLoader() {
//		// 初始化ImageLoader
//		@SuppressWarnings("deprecation")
//		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
//				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
//				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
//				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
//				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//						// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
//				.build(); // 创建配置过得DisplayImageOption对象
//
//		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//				.defaultDisplayImageOptions(options)
//
//				.threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
//
//				.discCacheFileNameGenerator(new Md5FileNameGenerator()).
//						tasksProcessingOrder(QueueProcessingType.LIFO).build();
//		ImageLoader.getInstance().init(config);
//	}

	/**
	 * ImageLoader 图片组件初始化
	 * 
	 * @param context
	 */
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.

		DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.mipmap.appmain_subject_1) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.mipmap.appmain_subject_1) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.mipmap.appmain_subject_1) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
						// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove
									// for
									// release
									// app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}