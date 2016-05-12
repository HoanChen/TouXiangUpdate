package com.tiqiuzhe.touxiangupdate.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.utils.UIUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ================================================================
 * 版权: 爱车小屋所有（C） 2016
 * <p/>
 * 作者：刘付文 （61128910@qq.com）
 * <p/>
 * 时间: 2016-04-16 15:25
 * <p/>
 * 描述：
 * ================================================================
 */
public class BannerView extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnTouchListener {
    protected ViewPageCompat mViewPager;// 轮播的ViewPager
    protected LinearLayout mIndicatorContainer;// 轮播选中的点
    private List<String> mDatas;

    private SwitchTask mTask;

    private static final int TIME = 5;

    private int mCurrentItem;

    // 定时任务
    private ScheduledExecutorService mPool;
    private OnBannerClickLinstener mLinstener;
    private View mFlBanner;


    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTask = new SwitchTask();
        setOnTouchListener(this);
    }

    public void refreshUI(List data) {
        this.mDatas = data;
        removeAllViews();
        stop();
        //AppUtils.inflate(R.layout.item_home_picture);
        View view = View.inflate(getContext(), R.layout.item_home_picture, this);
        //addView(view);
        //bind
        mFlBanner = view.findViewById(R.id.id_fl_banner);
        mViewPager = (ViewPageCompat) view.findViewById(R.id.item_home_picture_pager);
        mIndicatorContainer = (LinearLayout) view.findViewById(R.id.item_home_picture_container_indicator);

        // 设置Adapter
        mViewPager.setAdapter(new PicAdapter());

        // 设置点
        for (int i = 0; i < data.size(); i++) {

            View point = new View(getContext());
            point.setBackgroundResource(R.mipmap.img_red);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) (UIUtils.dip2px(6) + 0.5f),
                    (int) (UIUtils.dip2px(6) + 0.5f));
            if (i != 0) {
                params.leftMargin = (int) UIUtils.dip2px(6);
                params.bottomMargin = (int) UIUtils.dip2px(10);
            } else {
                //设置默认选中
                point.setBackgroundResource(R.mipmap.img_white);
            }
            mIndicatorContainer.addView(point, params);
        }

        mIndicatorContainer.setVisibility(View.GONE);
        //给viewpager设置监听
        mViewPager.addOnPageChangeListener(this);

        //设置选中
        int item = Integer.MAX_VALUE / 2;
        item = item - item % mDatas.size();
        mViewPager.setCurrentItem(item);

        mViewPager.requestDisallowInterceptTouchEvent(true);
        //自动轮播
        // start();
        //设置touch监听
        //mViewPager.setOnTouchListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        position = position % mDatas.size();

        //改变indicator选中
        int count = mIndicatorContainer.getChildCount();

        for (int i = 0; i < count; i++) {
            View view = mIndicatorContainer.getChildAt(i);

            view.setBackgroundResource(position == i
                    ? R.mipmap.img_red
                    : R.mipmap.img_white);
//                    ? R.drawable.img_red
//                    : R.drawable.point_normal);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 移除所有的消息
     */
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        start();
        super.onAttachedToWindow();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //停止轮播
                stop();
                break;
            case MotionEvent.ACTION_CANCEL:
                start();
                break;
            case MotionEvent.ACTION_UP:
                start();
                break;
        }
        return false;
    }


    private class SwitchTask
            implements Runnable {


        @Override
        public void run() {
            synchronized (mViewPager) {
                mCurrentItem = mViewPager.getCurrentItem();
                mCurrentItem++;
                handler.obtainMessage().sendToTarget();
            }

        }


    }

    // Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            synchronized (mViewPager) {
                mViewPager.setCurrentItem(mCurrentItem);
            }

        }

    };

    public boolean isShowPoint;

    public void setIsShowPoint(boolean res) {//是否显示滑动点
        this.isShowPoint = res;
    }

    public void start() {
        if (mDatas.size() > 1) {
            mIndicatorContainer.setVisibility(isShowPoint ? View.GONE : View.VISIBLE);
            if (mPool != null && !mPool.isShutdown()) {
                mPool.shutdownNow();
            }
            mPool = Executors.newSingleThreadScheduledExecutor();
            mPool.scheduleAtFixedRate(mTask, TIME, TIME, TimeUnit.SECONDS);
        }
    }

    public void stop() {
        if (mPool != null && !mPool.isShutdown()) {
            mPool.shutdownNow();
            mPool = null;
        }
    }

    private class PicAdapter
            extends PagerAdapter {

        @Override
        public int getCount() {

            if (mDatas != null) {// 如果长度为1就不轮播
                return mDatas.size() == 1 ? mDatas.size() : Integer.MAX_VALUE;
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position = position % mDatas.size();
            //  View view = AppUtils.inflate(R.layout.main_item_looper_pic);
            ImageView iv = new ImageView(getContext());
            String url = mDatas.get(position);
//            BmUtils.displayImage(iv, url, R.drawable.trans);
            Picasso.with(getContext()).load(url)
                   .into(iv);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            final int pos = position;
            iv.setOnTouchListener(BannerView.this);
            iv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mLinstener != null) {
                        mLinstener.onItemClick(pos);
                    }
                }
            });
            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }


    }

    public void setOnBannerClickLinstener(OnBannerClickLinstener linstener) {
        this.mLinstener = linstener;
    }

    public interface OnBannerClickLinstener {
        void onItemClick(int position);
    }

    public void setFLBannerVisible(boolean visible) {

        if (mFlBanner != null) {
            if (visible) {
                mFlBanner.setVisibility(View.VISIBLE);
            } else {
                mFlBanner.setVisibility(View.GONE);
            }
        }
    }
}
