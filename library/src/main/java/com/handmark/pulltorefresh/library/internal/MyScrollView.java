package com.handmark.pulltorefresh.library.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 项目名称：V积分 </br>
 * 文件名：MyScrollView.java</br>
 * 作者：吴承磊   </br>
 * 创建时间：2014-5-14</br>      
 * 功能描述: </br>      
 * 版本 V 1.0</br>               
 * 修改履历</br>
 * 日期      原因  BUG号    修改人 修改版本</br>
 */
public class MyScrollView extends ScrollView {
    private float xDistance, yDistance, lastX, lastY;

    public MyScrollView(Context context) {
        super(context);
    }
    
    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
    }
    
    public MyScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
            xDistance = yDistance = 0f;
            lastX = ev.getX();
            lastY = ev.getY();
            break;
        case MotionEvent.ACTION_MOVE:
            final float curX = ev.getX();
            final float curY = ev.getY();
            xDistance += Math.abs(curX - lastX);
            yDistance += Math.abs(curY - lastY);
            lastX = curX;
            lastY = curY;
            if (xDistance > yDistance)
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
