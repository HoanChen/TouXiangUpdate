package com.tiqiuzhe.touxiangupdate.view;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/*
 *  @项目名：  GooglePlay 
 *  @包名：    org.itheima15.googleplay.view
 *  @文件名:   ViewPageCompat
 *  @创建者:   Administrator
 *  @创建时间:  2015/11/24 16:27
 *
 */
public class ViewPageCompat
        extends ViewPager
{
    public ViewPageCompat(Context context) {
        this(context, null);
    }

    public ViewPageCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

        return super.dispatchTouchEvent(ev);
    }

}
