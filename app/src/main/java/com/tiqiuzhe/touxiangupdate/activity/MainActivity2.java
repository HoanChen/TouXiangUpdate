package com.tiqiuzhe.touxiangupdate.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tiqiuzhe.touxiangupdate.R;

/**
 * Created by android on 16/3/9.
 */
public class MainActivity2 extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {

      /*
        *
        *
        //广播-------------------------------------------------------------------------------
        private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("action.refreshFriend")) {
                    me.getTeamarray().remove(last_position);
                    mtAdapter.notifyDataSetChanged();
                }
            }
        };

        private void initMyBordcast() {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("action.refreshFriend");
            registerReceiver(mRefreshBroadcastReceiver, intentFilter);
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            EventBus.INSTANCE.unregister(this);
            //        unregisterReceiver(mRefreshBroadcastReceiver);
        }


//        Intent intent = new Intent("action.refreshFriend");
//        IntroActivity.this.sendBroadcast(intent);
//        IntroActivity.this.finish();
// android:windowSoftInputMode="adjustUnspecified|stateHidden"


        //集合遍历重复元素-------------------------------------------------------------------------------

       {

        List<String> teamColorold = new ArrayList<>();
        List<String> teamColor = new ArrayList<>();
        for (CheckBox check : boxList) {
            if (check.isChecked()) {
                teamColorold.add(check.getTag() + "");
            }
        }
        //根据集合对象获取迭代器对象
        Iterator<String> itold = teamColorold.iterator();

        //遍历并且输出
//        while (itold.hasNext()) {
//            String s = (String) itold.next();
//            Log.e("PAOPAO", "返回队服颜色旧的单个颜色值==" + s);
//        }
//        Log.e("PAOPAO", "返回队服颜色旧的集合==" + teamColorold.size());

        for (int i = 0; i < teamColorold.size(); i++) {
            String string = teamColorold.get(i);
            if (!teamColor.contains(string)) {
                teamColor.add(string);
            }
        }
        //根据集合对象获取迭代器对象
//        Iterator<String> it = teamColor.iterator();

        //遍历并且输出
//        while (it.hasNext()) {
//            String s = (String) it.next();
//            Log.e("PAOPAO", "返回队服颜色新的单个颜色值==" + s);
//        }
//        Log.e("PAOPAO", "返回队服颜色新的集合==" + teamColor.size());

        return teamColor;
    }


 //PopupWindow-----------------------------------------------------------------------------

        popupWindow_appearance = new PopupWindow(popView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,false);

			// 需要设置一下此参数，点击外边可消失
//			popupWindow_appearance.setBackgroundDrawable(new BitmapDrawable());
			// 设置点击窗口外边窗口消失
//						popupWindow_appearance.setOutsideTouchable(true);
			// 设置此参数获得焦点，否则无法点击
			//			popupWindow_appearance.setFocusable(true);

			popView.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (popupWindow_appearance!=null && popupWindow_appearance.isShowing()){
						popupWindow_appearance.dismiss();
						popupWindow_appearance=null;
					}
					return false;
				}
			});

		}

		backgroundAlpha(0.6f);
		//添加pop窗口关闭事件
		popupWindow_appearance.setOnDismissListener(new poponDismissListener());

		int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
		ll_quit_appearance.measure(w, h);
		int width = ll_quit_appearance.getMeasuredWidth();
		int height = ll_quit_appearance.getMeasuredHeight();

		//		Log.e("PAOPAO", "是不是重复的bean.getPlayerid() =" + bean.getPlayerid() + "--bean.getName()== " + bean.getName());
		//		popupWindow.showAsDropDown(view);
		//		popupWindow.showAtLocation(view, Gravity.CENTER | Gravity.TOP, 0,0);
		popupWindow_appearance.showAsDropDown(view,-(width - view.getWidth()) / 2, -(view.getHeight() + height));// 在某个布局(view)的正上方展示




//设置添加屏幕的背景透明度
	public void backgroundAlpha(float bgAlpha) {
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		lp.alpha = bgAlpha; //0.0-1.0
		getWindow().setAttributes(lp);
	}
        //添加弹出的popWin关闭的事件，主要是为了将背景透明度改回来
	class poponDismissListener implements PopupWindow.OnDismissListener {
		@Override
		public void onDismiss() {
			backgroundAlpha(1f);
		}
	}

 //map集合储存对象--------遍历集合---------------------请求参数------------------------------------------------

    private void setChuQin() {
		String playerlist;
		try {
			JSONArray jsonArray = new JSONArray();
			Set<Long> set = map.keySet();
			for (Long key : set) {
				AttendanceItem value = map.get(key);
				long playerid = value.getPlayerid();
				int status = value.getStatus();

				JSONObject jo = new JSONObject();
				jo.put("playerid", playerid);
				jo.put("status", status);

				jsonArray.put(jo);
			}

			playerlist = URLEncoder.encode(String.valueOf(jsonArray), "UTF-8");

			String url = ProtUtil.PATH
					+ "updatelineup?token=" + SharedUtil.getString(getApplicationContext(), "login_token")
					+ "&userid=" + SharedUtil.getString(getApplicationContext(), "user_id")
					+ "&matchid=" + matchid + "&playerlist=" + playerlist;

			//			Log.e("PAOPAO", "设置出勤请求参数url==" + url);
			new SetChuQinTask().execute(url);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}


        * **/

    }

}
