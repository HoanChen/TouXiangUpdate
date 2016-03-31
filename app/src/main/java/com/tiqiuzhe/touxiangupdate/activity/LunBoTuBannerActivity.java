package com.tiqiuzhe.touxiangupdate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.utils.DensityUtil;
import com.tiqiuzhe.touxiangupdate.view.banner.CBViewHolderCreator;
import com.tiqiuzhe.touxiangupdate.view.banner.ConvenientBanner;
import com.tiqiuzhe.touxiangupdate.view.banner.NetworkImageHolderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by android on 16/3/11.
 */
public class LunBoTuBannerActivity extends AppCompatActivity {

    @Bind(R.id.fragment1_banner)
    ConvenientBanner<String> convenientBanner; // 顶部广告栏控件

    List<String> banaerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbotu_banner);

        ButterKnife.bind(this);

        init();
    }

    private void init() {

        banaerList = new ArrayList<String>();

        banaerList.add("http://imgsrc.baidu.com/forum/pic/item/09be3f094b36acaf0ad6eb717cd98d1000e99cde.jpg");
        banaerList.add("http://attach2.scimg.cn/forum/201503/17/172006zr3762gfgdr5tmu9.jpg");
        banaerList.add("http://imgsrc.baidu.com/forum/pic/item/4fe0821349540923bc3560f39258d109b2de49b4.jpg");
        banaerList.add("http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1307/10/c6/23169101_1373445265040.jpg");

        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                                              @Override
                                              public NetworkImageHolderView createHolder() {
                                                  return new NetworkImageHolderView();
                                              }
                                          },banaerList).setPageIndicator(
                new int[] { R.mipmap.image_undian,
                        R.mipmap.image_selectdian });
        // 开始自动翻页
        convenientBanner.startTurning(3000);
        convenientBanner.setFocusable(true);
        convenientBanner.setFocusableInTouchMode(true);
        convenientBanner.requestFocus();
        convenientBanner.setLayoutParams(new LinearLayout.LayoutParams(DensityUtil.ScreenWh(LunBoTuBannerActivity.this)[0],
                                                                       (int) (DensityUtil.ScreenWh(LunBoTuBannerActivity.this)[0] / 3 * 1.5)));

    }
}
