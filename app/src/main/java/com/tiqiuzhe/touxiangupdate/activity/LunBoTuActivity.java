package com.tiqiuzhe.touxiangupdate.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils.TruncateAt;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher.ViewFactory;

import com.jauker.widget.BadgeView;
import com.shanping.shimmer.Shimmer;
import com.shanping.shimmer.ShimmerTextView;
import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.view.SlideShowView;
import com.tiqiuzhe.touxiangupdate.view.SlideShowView.OnItemClickListener;
import com.tiqiuzhe.touxiangupdate.view.jd.FirstSetpView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 16/3/9.
 */
public class LunBoTuActivity extends AppCompatActivity implements ViewFactory{

    //首页轮播
    @Bind(R.id.viewPager_menu)
    SlideShowView slideshowView;
    @Bind(R.id.switcher)
    TextSwitcher switcher;//站内公告

    @Bind(R.id.shimmer_tv)
    ShimmerTextView stv;

    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.bt)
    Button bt;
    @Bind(R.id.iv)
    ImageView iv;
    @Bind(R.id.ll)
    LinearLayout ll;

    @Bind(R.id.seekbar)
    SeekBar sb;
    @Bind(R.id.firstview)
    FirstSetpView fsv;


    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};


    Handler handler;
    String [] resources={
            "身是菩提树",
            "心如明镜台",
            "时时勤拂拭",
            "勿使惹尘埃"
    };
    int id= 0; //resources 数组的Id;
    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    id = next(); //更新Id值
                    updateText();  //更新TextSwitcherd显示内容;
                    break;
            }
        };
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunbotu);
        ButterKnife.bind(this);

        initViewPager();

        initShimmer();

        initBadgeView();

        initListener();
    }

    private void initListener() {
        sb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float currentProgress = (float) seekBar.getProgress() / (float) seekBar.getMax();
                fsv.setCurrentProgress(currentProgress);
                fsv.invalidate();
            }
        });
    }

    private void initBadgeView() {

//        1. setTargetView(View) --> 设置哪个控件显示数字提醒，参数就是一个view对象
//        2. setBadgeCount(int) --> 设置提醒的数字
//        3. setBadgeGravity(Gravity) --> 设置badgeview的显示位置
//        4. setBackgroundColor() --> 设置badgeview的背景色，当然还可以设置背景图片
//        5. setBackgroundResource() --> 设置背景图片
//        6. setTypeface() --> 设置显示的字体
//        7. setShadowLayer() --> 设置字体的阴影
//  注意   8.图片貌似不能使用badgeview

        //        BadgeView badgeView = new BadgeView(this);
        //        badgeView.setTargetView(rl_my_message);
        //        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        //        //        badgeView.setBackgroundColor(Color.RED);
        //        //        badgeView.setBadgeMargin(-1);
        //        //        badgeView.setTextColor(Color.BLACK);
        //        badgeView.setBadgeCount(0);

        //        badgeView.setBadgeGravity(Gravity.TOP | Gravity.RIGHT);
        //        badgeView.setBadgeGravity(Gravity.TOP | Gravity.LEFT);
        //        badgeView.setTypeface(Typeface.create(Typeface.SANS_SERIF,
        //                                              Typeface.ITALIC));
        //        badgeView.setShadowLayer(2, -1, -1, Color.GREEN);

        //        badgeView.setBadgeCount(me.getTeamarray().size());

        BadgeView badgeView = new BadgeView(this);
        badgeView.setTargetView(tv);
        badgeView.setBadgeCount(3);

        badgeView = new BadgeView(this);
        badgeView.setTargetView(bt);
        badgeView.setBadgeCount(-7);

        // 图片貌似不能使用badgeview
        badgeView = new BadgeView(this);
        badgeView.setTargetView(iv);
        badgeView.setBadgeCount(0);

        badgeView = new BadgeView(this);
        badgeView.setTargetView(ll);
        badgeView.setBackground(12, Color.parseColor("#9b2eef"));
        badgeView.setText("提示");

        badgeView = new BadgeView(this);
        badgeView.setTargetView(ll);
        badgeView.setBadgeGravity(Gravity.BOTTOM | Gravity.CENTER);
        badgeView.setBadgeCount(4);

        badgeView = new BadgeView(this);
        badgeView.setTargetView(ll);
        badgeView.setBadgeGravity(Gravity.CENTER);
        badgeView.setBackgroundColor(Color.RED);
//        badgeView.setBadgeMargin(-1);
        badgeView.setTextColor(Color.BLACK);
        badgeView.setBadgeCount(10);

        badgeView = new BadgeView(this);
        badgeView.setTargetView(ll);
        badgeView.setBadgeGravity(Gravity.LEFT | Gravity.CENTER);
        badgeView.setBackground(20, Color.RED);
        badgeView.setTextColor(Color.BLACK);
        badgeView.setBadgeCount(-6);

        badgeView = new BadgeView(this);
        badgeView.setTargetView(ll);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.LEFT);
        badgeView.setTypeface(Typeface.create(Typeface.SANS_SERIF,
                                              Typeface.ITALIC));
        badgeView.setShadowLayer(2, -1, -1, Color.GREEN);
        badgeView.setBadgeCount(2);
        //badgeView.setVisibility(View.GONE);

    }

    private void initShimmer() {
        //设置闪烁颜色
        stv.setShimmerColor(Color.GREEN);

        Shimmer s = new Shimmer();
        //设置闪烁次数，不设置为无限循环
//        s.setRepeatCount(3);
        //设置闪烁一次的时长，默认3000毫秒
        s.setDuration(8000);
        //设置每次开始闪烁时的延时，默认不延时
//        s.setStartDelay(0);
        //设置从左至右闪烁0，从右至左闪烁1
        s.setDirection(0);
        //闪烁监听
        s.setAnimatorListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        s.start(stv);
    }

    private void initViewPager() {

        slideshowView.setGravity(Gravity.CENTER);//设置小点的位置
        slideshowView.isAutoPlay(true);//设置是否启动轮播
        slideshowView.setAutoTime(3000);//设置轮播时间间隔
        slideshowView.initData(imageUrls);

        slideshowView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(new Intent(LunBoTuActivity.this, TouXiangActivity.class));
            }
        });


        initSwitcher();

    }

    private void initSwitcher() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTask(), 1, 5000);//每5秒更新

        switcher.setFactory(this);
        switcher.setInAnimation(AnimationUtils.loadAnimation(LunBoTuActivity.this, R.anim.slide_in_down));
        switcher.setOutAnimation(AnimationUtils.loadAnimation(LunBoTuActivity.this, R.anim.slide_out_up));

    }

    private int next(){

        int flag = id+1;
        if(flag>resources.length-1){
            flag=flag-resources.length;
        }
        return flag;
    }
    private void updateText(){
        switcher.setText(resources[id]);
    }

    public View makeView() {
        TextView tv =new TextView(LunBoTuActivity.this);
        tv.setTextSize(16);
        tv.setTextColor(getResources().getColor(R.color.tv_notice_color));
        tv.setEllipsize(TruncateAt.END);
        tv.setSingleLine(true);
        tv.setText(resources[id]);
        return tv;
    }


    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);

        }
    }
}
