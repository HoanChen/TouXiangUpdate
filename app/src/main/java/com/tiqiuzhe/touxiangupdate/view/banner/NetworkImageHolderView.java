package com.tiqiuzhe.touxiangupdate.view.banner;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.tiqiuzhe.touxiangupdate.R;
import com.tiqiuzhe.touxiangupdate.utils.ImageLoaderUtils;

/**
 * Created by Sai on 15/8/4. 网络图片加载例子
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
	private ImageView imageView;

	public View createView(Context context) {
		// 你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
		imageView = new ImageView(context);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		return imageView;
	}

	public void UpdateUI(Context context, final int position, String data) {
		imageView.setImageResource(R.mipmap.ic_default);
		ImageLoaderUtils.newInstance().loadImage(data, imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {

			}
		});
	}
}
