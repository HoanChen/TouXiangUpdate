package com.tiqiuzhe.touxiangupdate.activity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.shanping.shimmer.ShimmerTextView;
import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.utils.MyLog;
import com.tiqiuzhe.touxiangupdate.utils.ToastUtils;
import com.tiqiuzhe.touxiangupdate.view.BannerView;
import com.tiqiuzhe.touxiangupdate.view.BannerView.OnBannerClickLinstener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 16/5/12.
 */
public class TaoBaoActivity extends AppCompatActivity {
    public static final String TAG = TaoBaoActivity.class.getSimpleName();

    @Bind(R.id.bv)
    BannerView bv;

    @Bind(R.id.tv_remaintime)
    ShimmerTextView tv_remaintime;

    @Bind(R.id.tv_daojishi)
    ShimmerTextView tv_daojishi;

    private long countdownTime;//倒计时的总时间(单位:毫秒)
    private String timefromServer;//从服务器获取的订单生成时间
    private long chaoshitime;//从服务器获取订单有效时长(单位:毫秒)
    Handler handler = new Handler();

    private TimeCount time;

    List<String> mDatas=new ArrayList<>();
    private String[] imageUrls = {
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taobao);
        ButterKnife.bind(this);

        getTimeDuring();
        initData();

        time = new TimeCount(1*60*1000, 1000);
        time.start();
    }

    private void initData() {
//        mDatas.add("http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg");
        mDatas.add("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
        mDatas.add("http://pic18.nipic.com/20111215/577405_080531548148_2.jpg");
        mDatas.add("http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg");
//        mDatas.add("http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg");

        bv.refreshUI(mDatas);
        bv.setOnBannerClickLinstener(new OnBannerClickLinstener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showToast(TaoBaoActivity.this,"点击了第"+position+"个位置");
            }
        });
    }


    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countdownTime -= 1000;//倒计时总时间减1

//            MyLog.i(TAG,"countdownTime--"+countdownTime);

            SimpleDateFormat minforamt = new SimpleDateFormat("mm:ss");

            String hms = minforamt.format(countdownTime);//格式化倒计时的总时间
            tv_remaintime.setText("还剩下" + hms);
            handler.postDelayed(this, 1000);
        }
    };


    private void getTimeDuring() {
        chaoshitime = 1 * 60 * 1000;//应该从服务器获取

        timefromServer = "2019-04-04 16:40:50";//应该从服务器获取
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date serverDate = df.parse(timefromServer);

            long duringTime = new Date().getTime() - serverDate.getTime();//计算当前时间和从服务器获取的订单生成时间的时间差
            countdownTime = chaoshitime - duringTime;//计算倒计时的总时间

            handler.postDelayed(runnable, 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }
        @Override
        public void onFinish() {// 计时完毕时触发
            tv_daojishi.setText("倒计时结束");
        }
        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
//            String time = (int) millisUntilFinished / 1000+"";
            String time = (int) ((millisUntilFinished / 1000))+"";
            tv_daojishi.setText("请在"+time+"s 内完成付款");
        }
    }


}
