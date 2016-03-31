package com.tiqiuzhe.touxiangupdate.view.banner;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Sai on 15/7/29.
 * 缈婚〉鎸囩ず鍣ㄩ�閰嶅櫒
 */
public class CBPageChangeListener implements ViewPager.OnPageChangeListener {
    private ArrayList<ImageView> pointViews;
    private int[] page_indicatorId;
    public CBPageChangeListener(ArrayList<ImageView> pointViews,int page_indicatorId[]){
        this.pointViews=pointViews;
        this.page_indicatorId = page_indicatorId;
    }
    public void onPageScrollStateChanged(int arg0) {

    }

    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    public void onPageSelected(int index) {
        for (int i = 0; i < pointViews.size(); i++) {
            pointViews.get(index).setImageResource(page_indicatorId[1]);
            if (index != i) {
                pointViews.get(i).setImageResource(page_indicatorId[0]);
            }
        }
    }
}
