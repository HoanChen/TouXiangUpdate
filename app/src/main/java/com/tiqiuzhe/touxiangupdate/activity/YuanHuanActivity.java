package com.tiqiuzhe.touxiangupdate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.view.RoundProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 16/3/9.
 */
public class YuanHuanActivity extends AppCompatActivity {

    @Bind(R.id.rpb_detail)//百分比
     RoundProgressBar rpb_detail;
    @Bind(R.id.rpb_detail_up)//百分比------上面的
     RoundProgressBar rpb_detail_up;

    private int mTotalProgress = 80;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuanhuan);

        ButterKnife.bind(this);

        init();
    }

    private void init() {

        rpb_detail.setProgress(mTotalProgress);

        rpb_detail.setTextSize(30);

        rpb_detail_up.setTextSize(50);

        if (mTotalProgress==0) {

            rpb_detail_up.setText("已投满");
//            rpb_detail_up.setRotation(-45);
            rpb_detail_up.setTextColor(getResources().getColor(R.color.text_danhong));
            rpb_detail_up.setCricleColor(getResources().getColor(R.color.text_danhong));


            rpb_detail.setText("售罄");
            rpb_detail.setTextColor(getResources().getColor(R.color.iv_border_hui));
            rpb_detail.setCricleColor(getResources().getColor(R.color.iv_border_hui));
        }else{

            rpb_detail_up.setText("已投满");
            //            rpb_detail_up.setRotation(-45);
            rpb_detail_up.setTextColor(getResources().getColor(R.color.text_danhong));
            rpb_detail_up.setCricleColor(getResources().getColor(R.color.text_danhong));
//            rpb_detail_up.setVisibility(View.GONE);

            rpb_detail.setTextColor(getResources().getColor(R.color.text_jin));
            rpb_detail.setCricleColor(getResources().getColor(R.color.text_danjin));
        }

    }
}
