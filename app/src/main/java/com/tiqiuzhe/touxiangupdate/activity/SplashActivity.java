package com.tiqiuzhe.touxiangupdate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.utils.PrefUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 启动页面
 *
 * @ClassName: SplashActivity
 * @Description: TODO
 * @author zhuxc
 * @date modify by 2015-7-6 上午9:17:18
 * 启动画面 (1)判断是否是首次加载应用--采取读取SharedPreferences的方法
 * (2)是，则进入GuideActivity；否，则进入广告页 (3)3s后执行(2)操作
 *
 */
public class SplashActivity extends AppCompatActivity {

    @Bind(R.id.iv_splash)
    ImageView ads_iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                jumpNextPage();
            }
        },3000);

    }

    /**
     * 跳转下一个页面
     */
    private void jumpNextPage() {
        // 判断之前有没有显示过新手引导
        boolean userGuide = PrefUtils.getBoolean(this, "is_user_guide_showed",
                                                 false);

        if (!userGuide) {
            // 跳转到新手引导页
            startActivity(new Intent(SplashActivity.this, GuideActivity1.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }

        finish();
    }



}
