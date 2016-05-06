package com.tiqiuzhe.touxiangupdate.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.tiqiuzhe.touxiangupdate.CommApplication;
import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.bean.LifeItemData;

import java.util.ArrayList;
import java.util.List;

public class LifeAddActivity extends Activity {

	private ListView lv_life_list;
	private LifeListAdapter adapter;
	private List<LifeItemData> lifeItems = new ArrayList<LifeItemData>();
	private TextView tv_done;// 完成按钮

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_life_activity);
		lv_life_list = (ListView) findViewById(R.id.lv_life_list);
		tv_done = (TextView) findViewById(R.id.tv_done);
		initData();
	}

	/*
	 * 初始化数据
	 */
	private void initData() {
		lifeItems.add(new LifeItemData(R.mipmap.account, "我的账户"));
		lifeItems.add(new LifeItemData(R.mipmap.in, "转入"));
		lifeItems.add(new LifeItemData(R.mipmap.out, "转出"));
		lifeItems.add(new LifeItemData(R.mipmap.tx_details, "交易明细"));
		lifeItems.add(new LifeItemData(R.mipmap.my_products, "我的产品"));
		lifeItems.add(new LifeItemData(R.mipmap.my_cards, "我的银行卡"));
		lifeItems.add(new LifeItemData(R.mipmap.setting, "设置"));

		adapter = new LifeListAdapter();
		lv_life_list.setAdapter(adapter);
		// 完成按钮事件
		tv_done.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});
	}

	public List<LifeItemData> pos = CommApplication.getPos();

	class LifeListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return lifeItems.size();
		}

		@Override
		public Object getItem(int position){
			return lifeItems.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			
			final LifeItemData itemData = lifeItems.get(position);
			final ViewHolder holder;
			final View view;
			
			if (convertView == null) {
				view = View.inflate(LifeAddActivity.this, R.layout.item_life,
						null);
				holder = new ViewHolder();
				holder.ll_life_item = (LinearLayout) view
						.findViewById(R.id.ll_life_item);
				holder.iv_icon = (ImageView) view.findViewById(R.id.iv_icon);
				holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
				holder.cb_check = (CheckBox) view.findViewById(R.id.cb_status);
				
					if(pos.size()!=0){
					
					for(int i=0;i<pos.size();i++){
						if(pos.get(i).getName().equals(itemData.getName())){
							
							holder.cb_check.setChecked(true);
						}
					}
				}
				
				holder.ll_life_item.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						System.out.println("===click="+holder.cb_check.isChecked());
						if (holder.cb_check.isChecked()) {
							holder.cb_check.setChecked(false);

								for(int i=0;i<pos.size();i++){
									if(pos.get(i).getName().equals(itemData.getName())){
										pos.remove(i);
									}
								}
						} else {
							holder.cb_check.setChecked(true);
							if (!pos.contains(itemData)) {
								pos.add(itemData);
							}

						}
					}
				});

				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}

			holder.iv_icon.setImageResource(itemData.getIcon());
			holder.tv_name.setText(itemData.getName());
			return view;
		}

	}



	static class ViewHolder {
		LinearLayout ll_life_item;

		ImageView iv_icon;
		TextView tv_name;
		CheckBox cb_check;
	}

}
