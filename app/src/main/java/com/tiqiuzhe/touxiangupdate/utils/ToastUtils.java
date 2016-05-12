package com.tiqiuzhe.touxiangupdate.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	public static void showToast(Context ctx, String text) {
		Toast.makeText(ctx, text, Toast.LENGTH_SHORT).show();
	}
}
