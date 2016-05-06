package com.tiqiuzhe.touxiangupdate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.voiceads.AdError;
import com.iflytek.voiceads.AdKeys;
import com.iflytek.voiceads.IFLYAdListener;
import com.iflytek.voiceads.IFLYAdSize;
import com.iflytek.voiceads.IFLYFullScreenAd;
import com.tiqiuzhe.touxiangupdate.CommApplication;
import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.activity.LifeAddActivity;
import com.tiqiuzhe.touxiangupdate.bean.LifeItemData;

import java.util.ArrayList;
import java.util.List;

public class FragmentPage2 extends Fragment {

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
//		return inflater.inflate(R.layout.fragment_2, null);
//	}


	private GridView gv_life;
	LifeAdapter adapter;
	Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 12) {
				if (!items.contains("编辑")) {
					items.add("编辑");
					images.add(String.valueOf(R.mipmap.tianjia));
				}
				adapter.notifyDataSetChanged();
			}
		}

	};

	private List<String> items = new ArrayList<String>();
	private List<String> images = new ArrayList<String>();


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		if (!items.contains("编辑")) {
			items.add("编辑");
			images.add(String.valueOf(R.mipmap.tianjia));
		}

		View view = inflater.inflate(R.layout.fragment_life, container, false);
		gv_life = (GridView) view.findViewById(R.id.gv_life);
//		createAd();
		adapter = new LifeAdapter();
		gv_life.setAdapter(adapter);

		// 监听GridView的点击事件
		gv_life.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
									int position, long id) {

				if (items.get(position).equals("编辑")) {
					// 添加页面
					startActivityForResult(new Intent(getActivity(),
													  LifeAddActivity.class), 0);

				}

			}
		});
		return view;
	}

	class LifeAdapter extends BaseAdapter {

		private ImageView iv_icon;
		private TextView tv_item;

		@Override
		public int getCount() {

			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return items.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getActivity(), R.layout.life_list_view,
									 null);
			iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
			tv_item = (TextView) view.findViewById(R.id.tv_item);

			tv_item.setText(items.get(position));
			System.out.println("lv--" + position);
			iv_icon.setImageResource(Integer.valueOf(images.get(position)));

			return view;
		}

	}

	public void getData() {

		List<LifeItemData> list = CommApplication.getPos();

		for (int i = 0; i < list.size(); i++) {
			System.out.println("---" + list.get(i).getIcon());

			if (!items.contains(list.get(i).getName())) {
				items.add(list.get(i).getName());
				images.add(String.valueOf(list.get(i).getIcon()));

			}

		}

		if (items.size() > 1) {
			items.remove("编辑");
			images.remove(String.valueOf(R.mipmap.tianjia));
		}

		Message message = new Message();

		message.what = 12;

		FragmentPage2.this.myHandler.sendMessage(message);
		for (int i = 0; i < items.size(); i++) {
			System.out.println("--Name:" + items.get(i));
		}

	}

	@Override
	public void onResume() {
		items.clear();
		images.clear();
		super.onResume();
		getData();

	}

	private IFLYFullScreenAd ad;

	private void createAd() {
		ad = IFLYFullScreenAd.createFullScreenAd(getActivity(),
												 "740F71CA274749C75568ABF00D008542");
		ad.setAdSize(IFLYAdSize.FULLSCREEN);
		ad.setParameter(AdKeys.SHOW_TIME_FULLSCREEN, "6000");
		ad.setParameter(AdKeys.DOWNLOAD_ALERT, "true");
		ad.loadAd(new IFLYAdListener() {

			@Override
			public void onAdReceive() {
				System.out.println("===================");
				ad.showAd();
			}

			@Override
			public void onAdFailed(AdError arg0) {
				//				Toast.makeText(
				//						getApplicationContext(),
				//						arg0.getErrorCode() + "****"
				//								+ arg0.getErrorDescription(), 0).show();
				Toast.makeText(getActivity(), arg0.getErrorCode() + "****" + arg0.getErrorDescription(), 0).show();

			}

			@Override
			public void onAdExposure() {

			}

			@Override
			public void onAdClose() {

			}

			@Override
			public void onAdClick() {

			}
		});
	}







}