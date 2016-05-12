package com.tiqiuzhe.touxiangupdate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.utils.ToastUtils;
import com.tiqiuzhe.touxiangupdate.view.BannerView;
import com.tiqiuzhe.touxiangupdate.view.BannerView.OnBannerClickLinstener;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 16/5/12.
 */
public class TaoBaoActivity extends AppCompatActivity {

    @Bind(R.id.bv)
    BannerView bv;

    List<String> mDatas=new ArrayList<>();
    private String[] imageUrls = {"http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg",
            "http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg",
            "http://pic18.nipic.com/20111215/577405_080531548148_2.jpg",
            "http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg",
            "http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taobao);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {
        mDatas.add("http://img.taodiantong.cn/v55183/infoimg/2013-07/130720115322ky.jpg");
        mDatas.add("http://pic30.nipic.com/20130626/8174275_085522448172_2.jpg");
        mDatas.add("http://pic18.nipic.com/20111215/577405_080531548148_2.jpg");
        mDatas.add("http://pic15.nipic.com/20110722/2912365_092519919000_2.jpg");
        mDatas.add("http://pic.58pic.com/58pic/12/64/27/55U58PICrdX.jpg");

        bv.refreshUI(mDatas);
        bv.setOnBannerClickLinstener(new OnBannerClickLinstener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showToast(TaoBaoActivity.this,"点击了第"+position+"个位置");
            }
        });
    }
}
