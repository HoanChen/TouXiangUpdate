package com.tiqiuzhe.touxiangupdate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.tiqiuzhe.touxiangupdate.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by android on 16/3/11.
 */
public class ShuaXinActivity extends AppCompatActivity {

    @Bind(R.id.rlv_jifen_list)
    PullToRefreshListView rlv_jifen_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuaxin);
        ButterKnife.bind(this);

        initData();
    }

    private void initData() {

        ListView actualListView = rlv_jifen_list.getRefreshableView();
        // actualListView.setAdapter(liveAdapter);
        rlv_jifen_list.setMode(Mode.BOTH);

        rlv_jifen_list.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener2() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase refreshView) {
                rlv_jifen_list.getLoadingLayoutProxy().setPullLabel("正在载入…");
                rlv_jifen_list.getLoadingLayoutProxy().setRefreshingLabel("正在载入…");
                rlv_jifen_list.getLoadingLayoutProxy().setReleaseLabel("放开以刷新…");
                String label = DateUtils.formatDateTime(ShuaXinActivity.this, System.currentTimeMillis(),
                                                        DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                                        | DateUtils.FORMAT_ABBREV_ALL | DateUtils.FORMAT_SHOW_YEAR);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase refreshView) {

            }
        });

        rlv_jifen_list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });
    }
}
