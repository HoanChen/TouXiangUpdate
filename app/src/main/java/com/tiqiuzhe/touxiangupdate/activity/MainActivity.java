package com.tiqiuzhe.touxiangupdate.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.jauker.widget.BadgeView;
import com.shanping.shimmer.Shimmer;
import com.shanping.shimmer.ShimmerTextView;
import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.dialog.AlertDialog;
import com.tiqiuzhe.touxiangupdate.utils.LoadingDialog;
import com.tiqiuzhe.touxiangupdate.view.popupwindow.SelectTextPopupWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * Created by android on 16/3/9.
 */
public class MainActivity extends AppCompatActivity implements OnClickListener {

    @Bind(R.id.tv_fragment)
    ShimmerTextView tv_fragment;

    @Bind(R.id.tv_avatar)
    ShimmerTextView tv_avatar;

    @Bind(R.id.tv_yuanhuan)
    ShimmerTextView tv_yuanhuan;

    @Bind(R.id.tv_lunbotu)
    ShimmerTextView tv_lunbotu;

//    @Bind(R.id.tv_lunbotu_banner)
//    TextView tv_lunbotu_banner;

    @Bind(R.id.tv_yindaoye)
    ShimmerTextView tv_yindaoye;
    @Bind(R.id.tv_yindaoye1)
    ShimmerTextView tv_yindaoye1;


    @Bind(R.id.tv_tanchuang)
    ShimmerTextView tv_tanchuang;
    @Bind(R.id.tv_shuaxin)
    ShimmerTextView tv_shuaxin;

    @Bind(R.id.et_change)
    EditText et_change;

    @Bind(R.id.tv_dialog)
    ShimmerTextView  tv_dialog;

    @Bind(R.id.tv_more_click)
    ShimmerTextView tv_more_click;

    @Bind(R.id.tv_explosion)
    ShimmerTextView tv_explosion;

    @Bind(R.id.tv_jingdong_shuaxin)
    ShimmerTextView tv_jingdong_shuaxin;

    @Bind(R.id.tv_checkbox)
    ShimmerTextView tv_checkbox;

    @Bind(R.id.tv_ios_wheel)
    ShimmerTextView tv_ios_wheel;

    @Bind(R.id.tv_multiple)
    ShimmerTextView tv_multiple;


    private Shimmer s;
    private Dialog dialog;
    private BadgeView badgeView;
    private int i=1;

    Timer timer = new Timer();

    private long[] mHints = new long[3];//初始全部为0
    private List<String> mDatas;
    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initShimmer();

        initBadgeView();

//        initTime();
        initTime1();

    }

    @Override
    protected void onPause() {
        super.onPause();

        timer.cancel();
        timer.purge();
        if (timer!=null){
            timer = new Timer();
        }
    }

    private void initTime1() {
        // 定义Handler
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                //handler处理消息
                if(msg.what<100){
                    badgeView.setBadgeCount(msg.what);
                }else{
                    //在handler里可以更改UI组件
                    startActivity(new Intent(MainActivity.this, MainTabActivity.class));
                    timer.cancel();
                    timer=null;
                }
            }
        };
        // 定义计划任务，根据参数的不同可以完成以下种类的工作：
        // 在固定时间执行某任务，在固定时间开始重复执行某任务，重复时间间隔可控，
        // 在延迟多久后执行某任务，在延迟多久后重复执行某任务，重复时间间隔可控

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                Log.e("PAOPAO", "线程名称－－" + Thread.currentThread().getName());

                //定义一个消息传过去
                Message msg = new Message();
                msg.what = i++;
                handler.sendMessage(msg);

            }
        }, 500, 500);

    }

    private void initTime() {

        final Handler handler=new Handler();
          Runnable runnable=new Runnable() {
            @Override
            public void run() {
                i++;
                badgeView.setBadgeCount(i);
                handler.postDelayed(this, 2000);
            }
        };
//        2.启动计时器：//每两秒执行一次runnable.
        handler.postDelayed(runnable, 2000);
    }

    private void initBadgeView() {
        badgeView=new BadgeView(this);
        badgeView.setTargetView(tv_lunbotu);
//        badgeView.setBadgeCount(3);

//        badgeView.setBackgroundColor(Color.RED);
        //        badgeView.setBadgeMargin(-1);
//        badgeView.setPadding(10,10,10,10);
//        badgeView.setPadding(5,3,5,3);
//        badgeView.setPaddingRelative(5,5,5,5);
//        badgeView.setCompoundDrawablePadding(5);
//        badgeView.setIncludeFontPadding(true);
        badgeView.setBadgeMargin(5);
        //        badgeView.setTextColor(Color.BLACK);
        //        badgeView.setBadgeCount(Integer.parseInt("+"+99+""));
        badgeView.setBadgeCount(i);
    }

    private void initShimmer() {

        initShimmerColor();

        s = new Shimmer();
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

        initShimmerView();
    }

    private void initShimmerView() {
//        s.start();
        s.start(tv_fragment);
        s.start(tv_avatar);
        s.start(tv_tanchuang);
        s.start(tv_yuanhuan);
        s.start(tv_lunbotu);
        s.start(tv_yindaoye);
        s.start(tv_yindaoye1);
        s.start(tv_shuaxin);
        s.start(tv_dialog);
        s.start(tv_more_click);
        s.start(tv_explosion);

    }

    private void initShimmerColor() {
//        .setShimmerColor();
        //设置闪烁颜色
        tv_fragment.setShimmerColor(Color.GREEN);
        tv_avatar.setShimmerColor(Color.RED);
        tv_tanchuang.setShimmerColor(Color.BLACK);
        tv_yuanhuan.setShimmerColor(Color.BLUE);
        tv_lunbotu.setShimmerColor(Color.CYAN);
        tv_yindaoye.setShimmerColor(Color.DKGRAY);
        tv_yindaoye1.setShimmerColor(Color.GRAY);
        tv_shuaxin.setShimmerColor(Color.YELLOW);
        tv_dialog.setShimmerColor(Color.GREEN);
        tv_more_click.setShimmerColor(Color.RED);
        tv_explosion.setShimmerColor(Color.GREEN);
    }

    @Override
    @OnClick({R.id.tv_fragment,R.id.tv_avatar,R.id.tv_tanchuang,R.id.tv_yuanhuan,
            R.id.tv_lunbotu,R.id.tv_yindaoye,R.id.tv_yindaoye1,R.id.et_change,
            R.id.tv_shuaxin,R.id. tv_dialog,R.id.tv_more_click,R.id.tv_explosion,
            R.id.tv_jingdong_shuaxin,R.id.tv_checkbox,R.id.tv_ios_wheel,
            R.id.tv_multiple})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_fragment://FragmentTabHost实现
                startActivity(new Intent(this, MainTabActivity.class));
                break;

            case R.id.tv_lunbotu://轮播图
                startActivity(new Intent(this, LunBoTuActivity.class));
                break;

//            case R.id.tv_lunbotu_banner://轮播图banner
//                startActivity(new Intent(this, LunBoTuBannerActivity.class));
//                break;

            case R.id.tv_yindaoye://引导页
                startActivity(new Intent(this, GuideActivity.class));
                break;

            case R.id.tv_yindaoye1://引导页全图
                startActivity(new Intent(this, GuideActivity1.class));
                break;


            case R.id.tv_avatar://头像 及弹出弹窗
                startActivity(new Intent(this, TouXiangActivity.class));
                break;

            case R.id.tv_yuanhuan://圆环头像
                startActivity(new Intent(this, YuanHuanActivity.class));
                break;

            case R.id.tv_explosion://爆炸效果
                startActivity(new Intent(this, ExplosionActivity.class));
                break;

            case R.id.tv_tanchuang://及弹出弹窗
                upDialog();
                break;
            case R.id. tv_dialog:
                upJiaZaiDialog();
                break;

            case R.id.tv_shuaxin://下拉刷新
                startActivity(new Intent(this, ShuaXinActivity.class));
                break;

            case R.id.tv_jingdong_shuaxin://京东下拉刷新
                startActivity(new Intent(this, JDShuaXinActivity.class));
                break;

            case R.id.et_change://文本抖动
                shake();
                break;

            case R.id.tv_more_click://多次点击
                clickMore();
                break;

            case R.id.tv_checkbox://自定义 CheckBox
                startActivity(new Intent(this, CheckBoxActivity.class));
                break;

            case R.id.tv_multiple://多选 CheckBox
                startActivity(new Intent(this, MoreCheckBoxActivity.class));
                break;

            case R.id.tv_ios_wheel://仿ios  滚轮
                mDatas = new ArrayList<>();
                for (int i = 100; i <= 220; i++) {
                    mDatas.add(String.format("%dcm", i));
                }
                SelectTextPopupWindow heightPopupWindow = new SelectTextPopupWindow(MainActivity.this);
                heightPopupWindow.setEditText(tv_ios_wheel);
                heightPopupWindow.setDateList(mDatas,75);
                heightPopupWindow.show();
                break;
        }
    }



    private void clickMore() {

        //需要监听几次点击事件数组的长度就为几
        //如果要监听双击事件则数组长度为2，如果要监听3次连续点击事件则数组长度为3...
        // 参1表示原数组,参2表示从原数组哪个位置开始拷贝,参3是目标数组,参4表示要拷贝到目标数组哪一位,参4表示拷贝长度

        //将mHints数组内的所有元素左移一个位置
        System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
        //获得当前系统已经启动的时间
        mHints[mHints.length - 1] = SystemClock.uptimeMillis();
        if(SystemClock.uptimeMillis()-mHints[0]<=500){
            Toast.makeText(MainActivity.this, "点击了3次", Toast.LENGTH_LONG).show();
        }
    }

    private void upJiaZaiDialog() {
        dialog = LoadingDialog.createLoadingDialog(this, "正在加载中...");
        dialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        }, 2000);
    }

    private void shake() {
        et_change.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                initShake();
            }
        });
    }

    private void initShake() {
        // 开启震动的动画
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);

        // 自定义插补器
        // shake.setInterpolator(new Interpolator() {
        //
        // @Override
        // public float getInterpolation(float x) {
        // // y = ax + b
        // float y = x;
        // return y;
        // }
        // });

        et_change.startAnimation(shake);
        vibrate();
    }

    /**
     * 手机振动 需要权限: android.permission.VIBRATE
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        vibrator.vibrate(2000);//振动时间
        vibrator.vibrate(new long[] { 1000, 1000, 2000, 2000 }, -1);
        // (从1开始数)奇数位表示休息时间,偶数为振动时间
        // 参2表示循环方式,
        // -1表示只振动一次;0表示从第0个位置循环, 1表示从第1个位置循环
        //vibrator.cancel();//停止振动
    }

    private void upDialog() {

        String phone="15225885879";
        phone=phone.substring(0,3)+"****"+phone.substring(7,phone.length());

        new AlertDialog(MainActivity.this).builder().setTitle("退出当前账号")
//                .setMsg("确定退出吗？")
                .setMsg(phone)
                .setPositiveButton("确认退出", new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();

            } else {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

}
